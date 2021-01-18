package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessAlbum;
import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class AlbumService {

    private final DataAccessAlbum dataAccessAlbum;
    private final FileStoreService fileStoreService;

    @Autowired
    public AlbumService(@Qualifier("postgresAlbum") DataAccessAlbum dataAccessAlbum, FileStoreService fileStoreService) {
        this.dataAccessAlbum = dataAccessAlbum;
        this.fileStoreService = fileStoreService;
    }

    public List<Album> getAlbumsByUsername(String username){
        return dataAccessAlbum.getAlbumsByUsername(username);
    }

    public List<Album> getAlbumsByBandIdAndUsername(String username, int bandId) {
        return dataAccessAlbum.getAlbumsByBandIdAndUsername(username, bandId);
    }

    public byte[] downloadAlbumImage(int albumId) {
        Album album = getAlbumOrThrow(albumId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "albums");

        String key = String.format("%s-%s", album.getAlbumName(), album.getAlbumSpotifyId());

        return fileStoreService.download(path, key);
    }

    private Album getAlbumOrThrow(int albumId) {
        return getAlbumById(albumId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Album is not found!", albumId)));
    }

    public int uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file) {

        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "albums");

        String filename = String.format("%s-%s", albumDTO.getAlbumName(), albumDTO.getAlbumSpotifyId());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
            return dataAccessAlbum.addAlbum(albumDTO);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isImage(MultipartFile albumFile) {
        if(albumFile.isEmpty()){
            throw new IllegalStateException("AlbumFile is empty!");
        }
    }

    private void isFileEmpty(MultipartFile albumFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(albumFile.getContentType())){
            throw new IllegalStateException("AlbumFile type is not correct! [" + albumFile.getContentType() + "]");
        }
    }

    public int isAlbumExistBySpotifyId(String albumSpotifyId) {
        return dataAccessAlbum.isAlbumExistBySpotifyId(albumSpotifyId);
    }

    public int addAlbumForThisUser(int userId, int albumId) {
        return dataAccessAlbum.addAlbumForThisUser(userId, albumId);
    }

    public Optional<Album> getAlbumById(int albumId) {
        return dataAccessAlbum.getAlbumById(albumId);
    }

    public int deleteAlbumByIdAndUserId(int albumId, int userId){
        return dataAccessAlbum.deleteAlbumByIdAndUserId(albumId, userId);
    }

    public int getAlbumCountByUsername(String username) {
        return dataAccessAlbum.getAlbumCountByUsername(username);
    }
}

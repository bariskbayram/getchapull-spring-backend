package com.bkb.metalmusicreviews.backend.service.implementations;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dto.AlbumDTO;
import com.bkb.metalmusicreviews.backend.entity.Album;
import com.bkb.metalmusicreviews.backend.entity.Band;
import com.bkb.metalmusicreviews.backend.repository.AlbumRepository;
import com.bkb.metalmusicreviews.backend.service.FileStoreService;
import com.bkb.metalmusicreviews.backend.service.interfaces.AlbumServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service("jpaServiceAlbum")
public class AlbumService implements AlbumServiceInterface {

    private final AlbumRepository albumRepository;
    private final FileStoreService fileStoreService;

    @Autowired
    public AlbumService(@Qualifier("jpaRepoAlbum") AlbumRepository albumRepository, FileStoreService fileStoreService) {
        this.albumRepository = albumRepository;
        this.fileStoreService = fileStoreService;
    }

    @Override
    public List<Album> getAlbumsByUsername(String username) {
        return albumRepository.findAlbumsByUsername(username);
    }

    @Override
    public List<Album> getAlbumsByBandIdAndUsername(String username, int bandId) {
        return albumRepository.findAlbumsByUsernameAndBandId(username, bandId);
    }

    @Override
    public byte[] downloadAlbumImage(int albumId) {
        Album album = getAlbumOrThrow(albumId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "albums");
        String key = String.format("%s-%s", album.getAlbumName(), album.getAlbumSpotifyId());

        return fileStoreService.download(path, key);
    }

    private Album getAlbumOrThrow(int albumId) {
        return getAlbumById(albumId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Album is not found!", albumId)));
    }

    @Override
    public Album findAlbumByAlbumSpotifyId(String albumSpotifyId) {
        return albumRepository.findAlbumByAlbumSpotifyId(albumSpotifyId).orElse(null);
    }

    @Override
    public Album uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file) {
        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "albums");
        String filename = String.format("%s-%s", albumDTO.getAlbumName(), albumDTO.getAlbumSpotifyId());

        Album album = new Album(albumDTO.getAlbumSpotifyId(), albumDTO.getAlbumName(), albumDTO.getAlbumYear());

        //şimdilik yeni bir Band nesnesi oluşturuldu ama DB'den de çekilebilir
        //Yeni bir query daha çalıştırmamak için böyle yaptım. Mantıklıdır umarım :)
        album.setBand(new Band(albumDTO.getBandId()));

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
            return albumRepository.save(album);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isImage(MultipartFile albumFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(albumFile.getContentType())){
            throw new IllegalStateException("AlbumFile type is not correct! [" + albumFile.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile albumFile) {
        if(albumFile.isEmpty()){
            throw new IllegalStateException("AlbumFile is empty!");
        }
    }

    @Override
    public int addAlbumForThisUser(int userId, int albumId) {
        return albumRepository.insertUserAlbum(userId, albumId);
    }

    @Override
    public Optional<Album> getAlbumById(int albumId) {
        return albumRepository.findById(albumId);
    }

    @Override
    public int deleteAlbumByIdAndUserId(int albumId, int userId) {
        return albumRepository.deleteUserAlbum(userId, albumId);
    }

    @Override
    public int getAlbumCountByUsername(String username) {
        return albumRepository.getAlbumCountByUsername(username);
    }
}

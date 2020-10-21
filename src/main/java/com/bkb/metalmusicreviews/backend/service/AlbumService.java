package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessAlbum;
import com.bkb.metalmusicreviews.backend.model.Album;
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

    public List<Album> getAllAlbums(){
        return dataAccessAlbum.getAllAlbums();
    }

    public byte[] downloadAlbumImage(UUID albumId) {
        Album album = getAlbumOrThrow(albumId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profiles/bariskbayram/albums");

        return album.getCoverLink()
                .map(key -> fileStoreService.download(path, key))
                .orElse(new byte[0]);
    }

    private Album getAlbumOrThrow(UUID albumId) {
        return getAllAlbums()
                .stream()
                .filter(s -> s.getId().equals(albumId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Album is not found!", albumId)));
    }

    public byte[] downloadProfilePhoto() {
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profiles/bariskbayram/profilephotos");
        return fileStoreService.download(path, "bkb.jpg");
    }

    public void uploadAlbumFile(MultipartFile albumFile,
                                String albumTitle,
                                String bandName,
                                String year) {
        isImage(albumFile);
        isFileEmpty(albumFile);

        Album new_album = new Album(UUID.randomUUID(), bandName,albumTitle,year,"");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", albumFile.getContentType());
        metadata.put("Content-Length", String.valueOf(albumFile.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "profiles/bariskbayram/albums");

        String filename = String.format("%s-%s", albumFile.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), albumFile.getInputStream());
            new_album.setCoverLink(filename);
            dataAccessAlbum.addAlbum(new_album);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isFileEmpty(MultipartFile albumFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(albumFile.getContentType())){
            throw new IllegalStateException("AlbumFile type is not correct! [" + albumFile.getContentType() + "]");
        }
    }

    private void isImage(MultipartFile albumFile) {
        if(albumFile.isEmpty()){
            throw new IllegalStateException("AlbumFile is empty!");
        }
    }

    public Optional<Album> getAlbumById(UUID id) {
        return dataAccessAlbum.getAlbumById(id);
    }

    public int deleteAlbumById(UUID id){
        return dataAccessAlbum.deleteAlbumById(id);
    }

    public int updateAlbumById(UUID id, Album album){
        return dataAccessAlbum.updateAlbumById(id, album);
    }
}

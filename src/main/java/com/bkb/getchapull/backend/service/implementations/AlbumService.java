package com.bkb.getchapull.backend.service.implementations;

import com.bkb.getchapull.backend.bucket.BucketName;
import com.bkb.getchapull.backend.dto.AlbumDTO;
import com.bkb.getchapull.backend.entity.Album;
import com.bkb.getchapull.backend.entity.Band;
import com.bkb.getchapull.backend.repository.AlbumRepository;
import com.bkb.getchapull.backend.service.FileStoreService;
import com.bkb.getchapull.backend.service.interfaces.AlbumServiceInterface;

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
    public List<AlbumDTO> findAlbumsReviewedByUser(String username) {
        return albumRepository.findAlbumsReviewedByUser(username)
                .stream()
                .map(album -> new AlbumDTO(
                        album.getId(),
                        album.getName(),
                        album.getSpotifyId(),
                        album.getYear(),
                        album.getBand().getId()))
                .toList();
    }

    @Override
    public List<AlbumDTO> findBandAlbumsReviewedByUser(String username, Long bandId) {
        return albumRepository.findBandAlbumsReviewedByUser(username, bandId)
                .stream()
                .map(album -> new AlbumDTO(
                        album.getId(),
                        album.getName(),
                        album.getSpotifyId(),
                        album.getYear(),
                        album.getBand().getId()))
                .toList();
    }

    @Override
    public byte[] downloadAlbumImage(Long albumId) {
        Album album = getAlbumOrThrow(albumId);
        String key = String.format("albums/%s-%s", album.getName(), album.getSpotifyId());

        return fileStoreService.download(BucketName.IMAGE.getBucketName(), key);
    }

    private Album getAlbumOrThrow(Long albumId) {
        return getAlbumById(albumId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Album is not found!", albumId)));
    }

    @Override
    public Album findAlbumBySpotifyId(String albumSpotifyId) {
        return albumRepository.findAlbumBySpotifyId(albumSpotifyId).orElse(null);
    }

    @Override
    public Album uploadAlbumFile(AlbumDTO albumDTO, MultipartFile file) {
        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("albums/%s-%s", albumDTO.getName(), albumDTO.getSpotifyId());

        Album album = new Album(albumDTO.getSpotifyId(), albumDTO.getName(), albumDTO.getYear());
        album.setBand(new Band(albumDTO.getBandId()));

        try {
            fileStoreService.save(BucketName.IMAGE.getBucketName(), path, Optional.of(metadata), file.getInputStream());
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
    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }

    @Override
    public int getReviewedAlbumCountByUser(String username) {
        return albumRepository.getReviewedAlbumCountByUser(username);
    }
}

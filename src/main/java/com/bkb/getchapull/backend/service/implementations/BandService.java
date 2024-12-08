package com.bkb.getchapull.backend.service.implementations;

import com.bkb.getchapull.backend.bucket.BucketName;
import com.bkb.getchapull.backend.dto.BandDTO;
import com.bkb.getchapull.backend.entity.Band;
import com.bkb.getchapull.backend.repository.BandRepository;
import com.bkb.getchapull.backend.service.FileStoreService;
import com.bkb.getchapull.backend.service.interfaces.BandServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service("jpaServiceBand")
public class BandService implements BandServiceInterface {

    private final BandRepository bandRepository;
    private final FileStoreService fileStoreService;

    @Autowired
    public BandService(@Qualifier("jpaRepoBand") BandRepository bandRepository, FileStoreService fileStoreService) {
        this.bandRepository = bandRepository;
        this.fileStoreService = fileStoreService;
    }

    @Override
    public List<BandDTO> findBandsReviewedByUser(String username) {
        return bandRepository.findBandsReviewedByUser(username)
                .stream()
                .map(band -> new BandDTO(
                        band.getId(),
                        band.getName(),
                        band.getSpotifyId()))
                .toList();
    }

    @Override
    public byte[] downloadBandImage(Long bandId) {
        Band band = getBandOrThrow(bandId);
        String key = String.format("bands/%s-%s", band.getName(), band.getSpotifyId());

        return fileStoreService.download(BucketName.IMAGE.getBucketName(), key);
    }

    private Band getBandOrThrow(Long bandId) {
        return getBandById(bandId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Band is not found!", bandId)));
    }

    @Override
    public Band findBandBySpotifyId(String bandSpotifyId) {
        return bandRepository.findBandBySpotifyId(bandSpotifyId).orElse(null);
    }

    @Override
    public Band uploadBandFile(BandDTO bandDTO, MultipartFile file) {
        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("bands/%s-%s", bandDTO.getName(), bandDTO.getSpotifyId());

        Band band = new Band(bandDTO.getSpotifyId(), bandDTO.getName());

        try {
            fileStoreService.save(BucketName.IMAGE.getBucketName(), path, Optional.of(metadata), file.getInputStream());
            return bandRepository.save(band);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isImage(MultipartFile bandFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(bandFile.getContentType())){
            throw new IllegalStateException("BandFile type is not correct! [" + bandFile.getContentType() + "]");
        }
    }

    private void isFileEmpty(MultipartFile bandFile) {
        if(bandFile.isEmpty()){
            throw new IllegalStateException("BandFile is empty!");
        }
    }

    @Override
    public Optional<Band> getBandById(Long bandId) {
        return bandRepository.findById(bandId);
    }

    @Override
    public int getReviewedBandCountByUser(String username) {
        return bandRepository.getReviewedBandCountByUser(username);
    }
}

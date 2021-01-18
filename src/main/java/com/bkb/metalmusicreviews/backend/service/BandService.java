package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessBand;
import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Service
public class BandService {

    private final DataAccessBand dataAccessBand;
    private final FileStoreService fileStoreService;

    @Autowired
    public BandService(@Qualifier("postresBand") DataAccessBand dataAccessBand, FileStoreService fileStoreService) {
        this.dataAccessBand = dataAccessBand;
        this.fileStoreService = fileStoreService;
    }

    public List<Band> getBandsByUsername(String username) {
        return dataAccessBand.getBandsByUsername(username);
    }

    public byte[] downloadBandImage(int bandId) {
        Band band = getBandOrThrow(bandId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "bands");

        String key = String.format("%s-%s", band.getBandName(), band.getBandSpotifyId());

        return fileStoreService.download(path, key);
    }

    private Band getBandOrThrow(int bandId) {
        return getBandById(bandId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Band is not found!", bandId)));
    }

    public int uploadBandFile(BandDTO bandDTO, MultipartFile file) {

        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "bands");

        String filename = String.format("%s-%s", bandDTO.getBandName(), bandDTO.getBandSpotifyId());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
            return dataAccessBand.addBand(bandDTO);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isImage(MultipartFile bandFile) {
        if(bandFile.isEmpty()){
            throw new IllegalStateException("BandFile is empty!");
        }
    }

    private void isFileEmpty(MultipartFile bandFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(bandFile.getContentType())){
            throw new IllegalStateException("BandFile type is not correct! [" + bandFile.getContentType() + "]");
        }
    }

    public int isBandExistBySpotifyId(String bandSpotifyId) {
        return dataAccessBand.isBandExistBySpotifyId(bandSpotifyId);
    }

    public Optional<Band> getBandById(int bandId) {
        return dataAccessBand.getBandById(bandId);
    }

    public int getBandCountByUsername(String username) {
        return dataAccessBand.getBandCountByUsername(username);
    }
}

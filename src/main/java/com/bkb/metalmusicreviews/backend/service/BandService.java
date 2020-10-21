package com.bkb.metalmusicreviews.backend.service;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dao.DataAccessBand;
import com.bkb.metalmusicreviews.backend.model.Band;
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

    public List<Band> getAllBands() {
        return dataAccessBand.getAllBands();
    }

    public byte[] downloadBandImage(UUID bandId) {
        Band band = getBandOrThrow(bandId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(),
                "profiles/bariskbayram/bands");

        return band.getPhotoLink()
                .map(key -> fileStoreService.download(path, key))
                .orElse(new byte[0]);
    }

    private Band getBandOrThrow(UUID bandId) {
        return getAllBands()
                .stream()
                .filter(s -> s.getId().equals(bandId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Band is not found!", bandId)));
    }

    public void uploadBandFile(MultipartFile bandFile, String bandName) {
        isImage(bandFile);
        isFileEmpty(bandFile);

        Band new_band = new Band(UUID.randomUUID(), bandName, "");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", bandFile.getContentType());
        metadata.put("Content-Length", String.valueOf(bandFile.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "profiles/bariskbayram/bands");

        String filename = String.format("%s-%s", bandFile.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), bandFile.getInputStream());
            new_band.setPhotoLink(filename);
            dataAccessBand.addBand(new_band);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void isFileEmpty(MultipartFile bandFile) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType()).contains(bandFile.getContentType())){
            throw new IllegalStateException("BandFile type is not correct! [" + bandFile.getContentType() + "]");
        }
    }

    private void isImage(MultipartFile bandFile) {
        if(bandFile.isEmpty()){
            throw new IllegalStateException("BandFile is empty!");
        }
    }
}

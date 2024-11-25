package com.bkb.metalmusicreviews.backend.service.implementations;

import com.bkb.metalmusicreviews.backend.bucket.BucketName;
import com.bkb.metalmusicreviews.backend.dto.BandDTO;
import com.bkb.metalmusicreviews.backend.entity.Band;
import com.bkb.metalmusicreviews.backend.repository.BandRepository;
import com.bkb.metalmusicreviews.backend.service.FileStoreService;
import com.bkb.metalmusicreviews.backend.service.interfaces.BandServiceInterface;
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
    public List<Band> getBandsByUsername(String username) {
        return bandRepository.findBandsByUsername(username);
    }

    @Override
    public byte[] downloadBandImage(int bandId) {
        Band band = getBandOrThrow(bandId);
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "bands");
        String key = String.format("%s-%s", band.getBandName(), band.getBandSpotifyId());

        return fileStoreService.download(path, key);
    }

    private Band getBandOrThrow(int bandId) {
        return getBandById(bandId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Band is not found!", bandId)));
    }

    @Override
    public Band findBandByBandSpotifyId(String bandSpotifyId) {
        return bandRepository.findBandByBandSpotifyId(bandSpotifyId).orElse(null);
    }

    @Override
    public Band uploadBandFile(BandDTO bandDTO, MultipartFile file) {
        isImage(file);
        isFileEmpty(file);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "bands");
        String filename = String.format("%s-%s", bandDTO.getBandName(), bandDTO.getBandSpotifyId());

        Band band = new Band(bandDTO.getBandSpotifyId(), bandDTO.getBandName());

        try {
            fileStoreService.save(path, filename, Optional.of(metadata), file.getInputStream());
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
    public Optional<Band> getBandById(int bandId) {
        return bandRepository.findById(bandId);
    }

    @Override
    public int getBandCountByUsername(String username) {
        return bandRepository.getBandCountByUsername(username);
    }
}

package com.bkb.metalmusicreviews.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MetalMusicReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetalMusicReviewsApplication.class, args);

    }

}


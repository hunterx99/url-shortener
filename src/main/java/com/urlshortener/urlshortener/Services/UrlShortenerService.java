package com.urlshortener.urlshortener.Services;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Repository.UrlShortenerRepo;
import com.urlshortener.urlshortener.Utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class UrlShortenerService {

    @Autowired
    UrlShortenerRepo urlShortenerRepo;

    public String shortUrl(UrlShortenerEntity urlShortener) {
        System.out.println(urlShortener.toString());

        if (!Utils.isValidEmail(urlShortener.getFullUrl())) {
            return "not a valid email";
        }

        Optional<UrlShortenerEntity> urlShortenerEntityFromUrl = urlShortenerRepo
                .findByFullUrl(urlShortener.getFullUrl());

        if (urlShortenerEntityFromUrl.isPresent()) {
            String shortUrl = urlShortenerEntityFromUrl
                    .get()
                    .getShortUrl();
            log.info("Url already present" + shortUrl);
            return shortUrl;
        }

        log.info("Provided Full url not found in DB, moving forward......");

        UrlShortenerEntity newUrlShortenerEntity = UrlShortenerEntity
                .builder()
                .fullUrl(urlShortener.getFullUrl())
                .shortUrl(Utils.convertFullUrlToShortUrl(urlShortener.getFullUrl()))
                .createdAt(Instant.now().toString())
                .build();

        return urlShortenerRepo.save(newUrlShortenerEntity)
                .getShortUrl();
    }
}

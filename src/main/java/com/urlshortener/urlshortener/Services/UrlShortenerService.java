package com.urlshortener.urlshortener.Services;

import com.urlshortener.urlshortener.Controller.IdGenerationController;
import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Repository.UrlShortenerRepo;
import com.urlshortener.urlshortener.Utils.CacheUtils;
import com.urlshortener.urlshortener.Utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UrlShortenerService {

    @Autowired
    UrlShortenerRepo urlShortenerRepo;

    @Autowired
    CacheUtils cacheUtils;

    @Autowired
    IdGenerationController idGenerationController;

//    @CacheEvict(value = "urls", allEntries = true)
    public String longUrlToShortUrl(UrlShortenerEntity urlShortener) {
        long startTime = System.currentTimeMillis();
        System.out.println(urlShortener.toString());

        if (!Utils.isValidEmail(urlShortener.getFullUrl())) {
            return "Not a valid email";
        }

        Optional<UrlShortenerEntity> urlShortenerEntityFromUrl = urlShortenerRepo
                    .findByFullUrl(urlShortener.getFullUrl());

        if (!urlShortenerEntityFromUrl.isPresent()) {
            log.info("making Db call for fullurl....");
            urlShortenerEntityFromUrl = urlShortenerRepo
                    .findByFullUrl(urlShortener.getFullUrl());
        }
        String baseUrl = Utils.getBaseUrl();
        if (urlShortenerEntityFromUrl.isPresent()) {
            String shortUrl = urlShortenerEntityFromUrl
                    .get()
                    .getShortUrl();
            log.info("Url already present {}" + shortUrl);
            return baseUrl + shortUrl;
        }

        log.info("Provided Full url not found in DB, moving forward......");

        int id = 0;
        String shortString = "";

        id = idGenerationController.getId();
        log.info("id generated {}", id);
        shortString = Utils.base10ToBase62(id);
//        while (true) {
//            String temp = getShortUrl(shortString);
//            log.info("temp {}", temp);
//            if (temp.equalsIgnoreCase("not found")) {
//                break;
//            }
//        };

        log.info("shortString generated {}", shortString);

        UrlShortenerEntity newUrlShortenerEntity = UrlShortenerEntity
                .builder()
                .fullUrl(urlShortener.getFullUrl())
                .shortUrl(shortString)
                .createdAt(Instant.now().toString())
                .expireAt(Instant.now().plus(60, ChronoUnit.DAYS).toString())
                .status("Active")
                .build();

        String newShortUrl = urlShortenerRepo.save(newUrlShortenerEntity)
                .getShortUrl();

        log.info("Time taken for {} {}", urlShortener.getFullUrl(), (System.currentTimeMillis() - startTime));
        return baseUrl + newShortUrl;
    }

    private Optional<UrlShortenerEntity> getDataFromCache(String fullUrl) {
        List<UrlShortenerEntity> allUrlDetailsFromCache = cacheUtils.getAllUrlDetails();

        return allUrlDetailsFromCache
                .stream()
                .filter(eachUrl -> eachUrl.getFullUrl().equals(fullUrl))
                .findFirst();
    }

//    @Cacheable("urls")
    public List<UrlShortenerEntity> getAllUrlDetails() {
        log.info("Calling DB to get all urls...");
        return urlShortenerRepo.findAll();
    }

//    @Cacheable("urls")
    public String shortUrlToLongUrl(String shortUrl) {
        Optional<UrlShortenerEntity> urlShortenerEntityByShortUrl = urlShortenerRepo.findByShortUrl(shortUrl);

        return urlShortenerEntityByShortUrl
                .map(UrlShortenerEntity::getFullUrl)
                .orElse("Not Found");
    }

    public String getShortUrl(String shortUrl) {
        log.info("making db call for shortUrl {}", shortUrl);
        Optional<UrlShortenerEntity> urlShortenerEntity = urlShortenerRepo.findByShortUrl(shortUrl);

        return urlShortenerEntity
                .map(UrlShortenerEntity::getShortUrl)
                .orElse("not found");
    }
}

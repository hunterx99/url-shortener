package com.urlshortener.urlshortener.Services;

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

    @CacheEvict(value = "urls", allEntries = true)
    public String longUrlToShortUrl(UrlShortenerEntity urlShortener) {
        System.out.println(urlShortener.toString());

        if (!Utils.isValidEmail(urlShortener.getFullUrl())) {
            return "Not a valid email";
        }

        Optional<UrlShortenerEntity> urlShortenerEntityFromUrl = getDataFromCache(urlShortener.getFullUrl());
//        Optional<UrlShortenerEntity> urlShortenerEntityFromUrl = urlShortenerRepo
//                    .findByFullUrl(urlShortener.getFullUrl());

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

        UrlShortenerEntity newUrlShortenerEntity = UrlShortenerEntity
                .builder()
                .fullUrl(urlShortener.getFullUrl())
                .shortUrl(Utils.convertFullUrlToShortUrl(urlShortener.getFullUrl()))
                .createdAt(Instant.now().toString())
                .expireAt(Instant.now().plus(60, ChronoUnit.DAYS).toString())
                .status("Active")
                .build();

        return baseUrl + urlShortenerRepo.save(newUrlShortenerEntity)
                .getShortUrl();
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

    @Cacheable("urls")
    public String shortUrlToLongUrl(String shortUrl) {
        Optional<UrlShortenerEntity> urlShortenerEntityByShortUrl = urlShortenerRepo.findByShortUrl(shortUrl);

        return urlShortenerEntityByShortUrl
                .map(UrlShortenerEntity::getFullUrl)
                .orElse("Not Found");
    }
}

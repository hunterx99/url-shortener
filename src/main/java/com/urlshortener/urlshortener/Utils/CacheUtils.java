package com.urlshortener.urlshortener.Utils;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Repository.UrlShortenerRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class CacheUtils {
    @Autowired
    UrlShortenerRepo urlShortenerRepo;

    @Cacheable(value = "urls")
    public List<UrlShortenerEntity> getAllUrlDetails() {
        log.info("Calling DB to get all urls...");
        return urlShortenerRepo.findAll();
    }
}

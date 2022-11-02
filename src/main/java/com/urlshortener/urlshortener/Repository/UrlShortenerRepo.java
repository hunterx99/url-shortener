package com.urlshortener.urlshortener.Repository;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShortenerRepo extends JpaRepository<UrlShortenerEntity, Long> {
    Optional<UrlShortenerEntity> findByFullUrl(String fullUrl);

    Optional<UrlShortenerEntity> findByShortUrl(String shortUrl);
}

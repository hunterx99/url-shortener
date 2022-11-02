package com.urlshortener.urlshortener.Controller;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UrtShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<String> longUrlToShortUrl(@RequestBody UrlShortenerEntity urlShortener) {
        return new ResponseEntity<>(urlShortenerService.longUrlToShortUrl(urlShortener), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UrlShortenerEntity>> findAllUrls() {
        return new ResponseEntity<>(urlShortenerService.getAllUrlDetails(), HttpStatus.OK);
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<String> shortUrlToLongUrl(@PathVariable String shortUrl) {
        return new ResponseEntity<>(urlShortenerService.shortUrlToLongUrl(shortUrl), HttpStatus.OK);
    }

}

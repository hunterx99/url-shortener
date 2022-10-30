package com.urlshortener.urlshortener.Controller;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UrtShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<String> shortUrl(@RequestBody UrlShortenerEntity urlShortener) {
        return new ResponseEntity<>(urlShortenerService.shortUrl(urlShortener), HttpStatus.OK);
    }
}

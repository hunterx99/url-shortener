package com.urlshortener.urlshortener;

import com.urlshortener.urlshortener.Model.UrlShortenerEntity;
import com.urlshortener.urlshortener.Repository.UrlShortenerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

/*	@Autowired
	UrlShortenerRepo urlShortenerRepo;

	@EventListener(ApplicationReadyEvent.class)
	void init() {

			UrlShortenerEntity urlShortener= UrlShortenerEntity
					.builder()
					.fullUrl("www.google.com")
					.shortUrl("adjddhdbn")
					.build();
		urlShortenerRepo.save(urlShortener);

//		userRepo.save(user);
	}*/
}

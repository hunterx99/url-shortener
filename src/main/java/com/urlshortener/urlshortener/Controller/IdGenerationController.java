package com.urlshortener.urlshortener.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@Log4j2
public class IdGenerationController {

    @Autowired
    private RestTemplate template;

    public long getId() {
        log.info("Making rest call...");
        String url = "http://localhost:9091/getId";
        return template.getForObject(url, Long.class);
    }
}

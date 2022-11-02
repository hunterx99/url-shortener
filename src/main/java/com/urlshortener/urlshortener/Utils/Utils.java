package com.urlshortener.urlshortener.Utils;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;
public class Utils {

    public static String convertFullUrlToShortUrl(String fullUrl) {
        return String
                .valueOf(Hashing
                        .murmur3_32_fixed()
                        .hashString(fullUrl, StandardCharsets.UTF_8));
    }

    public static boolean isValidEmail(String email) {

        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );

        return urlValidator
                .isValid(email);
    }

    public static String getBaseUrl() {
        String baseUrl = "http://localhost:8080/";

        return baseUrl;
    }
}

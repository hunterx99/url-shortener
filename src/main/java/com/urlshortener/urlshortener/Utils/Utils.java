package com.urlshortener.urlshortener.Utils;

import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utils {

    private static final String ELEMENTS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

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

    public static String base10ToBase62(long n) {

        byte[] bytes = Longs.toByteArray(n);
        String encodedString = Base64.getUrlEncoder().encodeToString(bytes);

        encodedString = encodedString
                .replaceAll("=", "")
                .replaceAll("/", "-");

//        StringBuilder sb = new StringBuilder();
//
//        while (n != 0) {
//            sb.insert(0, ELEMENTS.charAt(n % 62));
//            n /= 62;
//        }
//        if(sb.length() < 7) {
//            while (sb.length() != 7) {
//                sb.insert(0, '0');
//            }
//        }
//        return sb.toString();
//        return encodedString.substring(0, 8);
        return encodedString;
    }
}

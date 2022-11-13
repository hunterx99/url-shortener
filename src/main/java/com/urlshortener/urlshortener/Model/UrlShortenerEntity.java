package com.urlshortener.urlshortener.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "urlshortener_seq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_url")
    private String fullUrl;
    @Column(name = "short_url")
    private String shortUrl;
    private String createdAt;
    private String expireAt;
    private String status;
}


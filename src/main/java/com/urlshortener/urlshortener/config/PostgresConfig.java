package com.urlshortener.urlshortener.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {
    @Bean
    public DataSource getDatSource () {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/urlshortener?createDatabaseIfNotExist=true");
        return dataSourceBuilder.build();
    }
}

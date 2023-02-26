package com.insignia.jnguyen.robotchallenge.config;

import com.insignia.jnguyen.robotchallenge.model.Board;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationConfiguration {
    @Value("${table.width:5}")
    private int width;

    @Value("${table.height:5}")
    private int height;

    @Bean
    Board board() {
        return new Board(width, height);
    }

}

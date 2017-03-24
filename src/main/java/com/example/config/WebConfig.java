package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by charmanesantiago on 29/03/2016.
 */
@Configuration
@EnableWebMvc
@ComponentScan
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {


    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer getPropertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "POST", "GET");
    }
}
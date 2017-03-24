package com.example.config;

import com.example.auth.filter.CustomAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by charmanesantiago on 29/02/2016.
 */

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Value("${demo.cors.secret.key}")
    private String secretKey;

    @Bean //needed to set path of application.properties which the @Value will use to resolve desired config
    public static PropertySourcesPlaceholderConfigurer getPropertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //add filter on all incoming request using custom filter CustomAuthenticationFilter
        http
                .addFilterBefore(new CustomAuthenticationFilter(secretKey), BasicAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll();

    }

    //Define the paths here you want to exclude in your custom filter
    //In this case, since we exclude these path, they don't have to pass the auth requirement from CustomAuthenticationFilter
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/ycbb");
        web.ignoring().antMatchers("/login");
        //TODO remove exclusion of /users if we wanna secure it
        //web.ignoring().antMatchers("/users");
        web.ignoring().antMatchers("/users/forgot_password");
        web.ignoring().antMatchers("/users/set_password");
    }

}

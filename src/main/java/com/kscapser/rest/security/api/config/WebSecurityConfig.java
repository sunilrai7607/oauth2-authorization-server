package com.kscapser.rest.security.api.config;

import com.kscapser.rest.security.api.service.ICustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final ICustomUserDetails customUserDetails;
    private final PasswordEncoder encoder;

    @Autowired
    public WebSecurityConfig(ICustomUserDetails customUserDetails, PasswordEncoder encoder) {
        this.customUserDetails = customUserDetails;
        this.encoder = encoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetails).passwordEncoder(encoder);
    }
}

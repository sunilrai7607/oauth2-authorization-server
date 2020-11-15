package com.kscapser.rest.security.api.config;

import com.kscapser.rest.security.api.common.properties.Oauth2Config;
import com.kscapser.rest.security.api.common.utility.CustomJwtTokenAccessConverter;
import com.kscapser.rest.security.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    private final PasswordEncoder encoder;
    private final Oauth2Config oauth2Config;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthorizationServerConfig(AuthenticationManager authenticationManager, DataSource dataSource, PasswordEncoder encoder, Oauth2Config oauth2Config, AccountRepository accountRepository) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.encoder = encoder;
        this.oauth2Config = oauth2Config;
        this.accountRepository = accountRepository;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(encoder).build();
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .tokenEnhancer(jwtAccessTokenConverter());
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new CustomJwtTokenAccessConverter(accountRepository);
        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource(oauth2Config.getJksConfigFileName()), oauth2Config.getKeyPassword().toCharArray()).getKeyPair(oauth2Config.getJksKeyAlias()));
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}

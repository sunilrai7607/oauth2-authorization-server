package com.kscapser.rest.security.api.common.utility;

import com.kscapser.rest.security.api.repository.AccountRepository;
import com.kscapser.rest.security.api.repository.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CustomJwtTokenAccessConverter extends JwtAccessTokenConverter {

    private final AccountRepository accountRepository;
    private final String CLIENT_CREDENTIALS = "client_credentials";


    @Autowired
    public CustomJwtTokenAccessConverter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (CLIENT_CREDENTIALS.equalsIgnoreCase(authentication.getOAuth2Request().getGrantType())) {
            return super.encode(accessToken, authentication);
        }

        DefaultOAuth2AccessToken customAccessToken = enhanceJwtToken(accessToken, authentication);
        return super.encode(customAccessToken, authentication);
    }

    private DefaultOAuth2AccessToken enhanceJwtToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        log.info("Called JwtTokenUtility.encode {}", authentication.getPrincipal());

        Account account = Optional.ofNullable(
                accountRepository.findByUserName(user.getUsername())).get()
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist")
                );

        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        info.put("email", account.getEmail());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);

        customAccessToken.setAdditionalInformation(info);
        return customAccessToken;
    }

    @Override
    protected Map<String, Object> decode(String token) {
        return super.decode(token);
    }
}

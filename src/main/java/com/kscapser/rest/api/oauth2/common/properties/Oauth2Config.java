package com.kscapser.rest.api.oauth2.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("oauth2-config")
public class Oauth2Config implements Serializable {


    private static final long serialVersionUID = -7945401595306118086L;

    public Boolean userScopes;
    private String jksConfigFileName;
    private String keyPassword;
    private String jksKeyAlias;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
}

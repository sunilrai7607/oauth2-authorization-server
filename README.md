# oauth2-authorization-server

```
Handle jwttoken enhancer for client_credential, password grant type
    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (CLIENT_CREDENTIALS.equalsIgnoreCase(authentication.getOAuth2Request().getGrantType())) {
            return super.encode(accessToken, authentication);
        }

        DefaultOAuth2AccessToken customAccessToken = enhanceJwtToken(accessToken, authentication);
        return super.encode(customAccessToken, authentication);
    }

```

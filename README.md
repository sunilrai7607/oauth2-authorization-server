# OAuth2-Authorization-Server
What is Oauth2 ?<br /> OAuth 2.0 is the industry-standard protocol for authorization. OAuth 2.0 focuses on client developer simplicity while providing specific authorization flows for web applications,
desktop applications, mobile phones, and living room devices.<br /> 

OAuth 2.0 has 4 roles:
- Authorization Server
- Resource Server
- Resource Owner
- Client Application

This an OAuth authorization server to server and generate barer token <br\>
Create Authorization Server by extending AuthorizationServerConfigurerAdapter
```java
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
   
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //In-memory
        //        clients.inMemory()
        //                .withClient("client-server")
        //                .secret(encoder().encode("client-secret"))
        //                .authorizedGrantTypes("client_credentials")
        //                .scopes("all");
        //Jdbc
        clients.jdbc(dataSource).passwordEncoder(encoder).build();
    }

}
```
Override configure(ClientDetailsServiceConfigurer clients) method. Based on requirement use either In-Memory or JDBC
using Jdbc to generate the token






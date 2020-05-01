package com.example.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Oauth2AuthorizationServerConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .scopes("scope")
                .resourceIds("resource")
                .authorities("authorities")
                // http://127.0.0.1:8080/oauth/authorize?client_id=client&response_type=code
                // curl -X POST 127.0.0.1:8080/oauth/token -d 'grant_type=authorization_code&client_id=client&client_secret=secret&code=oGXG2d' -v
                // http://127.0.0.1:8080/oauth/authorize?client_id=client&response_type=token
                // curl -X POST 127.0.0.1:8080/oauth/token -d 'grant_type=password&client_id=client&client_secret=secret&username=user&password=123' -v
                // curl -X POST 127.0.0.1:8080/oauth/token -d 'grant_type=client_credentials&client_id=client&client_secret=secret' -v
                // curl 127.0.0.1:8080/resource -H 'Authorization:Bearer f42632c6-6ea4-4326-883c-1b30cea1a0e8'
                .authorizedGrantTypes("authorization_code", "implicit", "password", "refresh_token", "client_credentials")
                .redirectUris("/")
                .accessTokenValiditySeconds(60 * 60)
                .refreshTokenValiditySeconds(60 * 60 * 6);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

}

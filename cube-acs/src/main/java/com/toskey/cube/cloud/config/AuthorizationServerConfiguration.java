package com.toskey.cube.cloud.config;

import com.toskey.cube.cloud.authentication.UserDetailsAuthenticationProvider;
import com.toskey.cube.cloud.authentication.password.PasswordAuthenticationConverter;
import com.toskey.cube.cloud.authentication.password.PasswordAuthenticationProvider;
import com.toskey.cube.cloud.authentication.sms.SmsCodeAuthenticationConverter;
import com.toskey.cube.cloud.authentication.sms.SmsCodeAuthenticationProvider;
import com.toskey.cube.cloud.handler.*;
import com.toskey.cube.cloud.token.AccessTokenGenerator;
import com.toskey.cube.cloud.token.TokenCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 统一认证装配
 *
 * @author toskey
 * @version 1.0.0
 */
@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class AuthorizationServerConfiguration {

    private final OAuth2AuthorizationService authorizationService;

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorizationServerConfiguration(OAuth2AuthorizationService authorizationService,
                                            RedisTemplate<String, Object> redisTemplate) {
        this.authorizationService = authorizationService;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            AuthorizationServerProperties authorizationServerProperties,
                                            ClientAuthenticationFailureHandler clientAuthenticationFailureHandler,
                                            TokenEndpointAuthenticationSuccessHandler tokenEndpointAuthenticationSuccessHandler,
                                            TokenEndpointAuthenticationFailureHandler tokenEndpointAuthenticationFailureHandler) throws Exception {

        OAuth2AuthorizationServerConfigurer configurer = new OAuth2AuthorizationServerConfigurer();

        AntPathRequestMatcher[] requestMatchers = Arrays.stream(authorizationServerProperties.getIgnorePaths())
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);

        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(requestMatchers)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint(authorizationServerProperties.getLoginEntryPoint()),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                ))
                .formLogin(formConfigurer ->
                        formConfigurer
                                .loginPage("/login")
                                .successHandler(new FormAuthenticationSuccessHandler())
                                .failureHandler(new FormAuthenticationFailureHandler()))
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutSuccessHandler(new OAuth2LogoutSuccessHandler())
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true))
                .csrf(AbstractHttpConfigurer::disable)
                .with(configurer.tokenEndpoint(tokenEndpoint ->
                                        tokenEndpoint
                                                .accessTokenRequestConverter(
                                                        new DelegatingAuthenticationConverter(Arrays.asList(
                                                                new PasswordAuthenticationConverter(),
                                                                new SmsCodeAuthenticationConverter()
                                                        ))
                                                )
                                                .accessTokenResponseHandler(tokenEndpointAuthenticationSuccessHandler)
                                                .errorResponseHandler(tokenEndpointAuthenticationFailureHandler)
                                )
                                .oidc(Customizer.withDefaults())
                                .clientAuthentication(clientAuthentication ->
                                        clientAuthentication.errorResponseHandler(clientAuthenticationFailureHandler)
                                )
                                .authorizationService(authorizationService)
                                .authorizationServerSettings(
                                        AuthorizationServerSettings
                                                .builder()
                                                .issuer(authorizationServerProperties.getIssuerUrl())
                                                .build()
                                ),
                        Customizer.withDefaults());

        DefaultSecurityFilterChain chain = http.build();
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        // 用户身份认证
        UserDetailsAuthenticationProvider userDetailsAuthenticationProvider = new UserDetailsAuthenticationProvider();
        userDetailsAuthenticationProvider.setRedisTemplate(redisTemplate);
        http.authenticationProvider(userDetailsAuthenticationProvider);
        // 密码模式前置处理
        http.authenticationProvider(new PasswordAuthenticationProvider(authorizationService, accessTokenGenerator(), authenticationManager));
        // 短信验证码模式前置处理
        http.authenticationProvider(new SmsCodeAuthenticationProvider(authorizationService, accessTokenGenerator(), authenticationManager));

        return chain;
    }

    @Bean
    public OAuth2TokenGenerator<OAuth2Token> accessTokenGenerator() {
        AccessTokenGenerator generator = new AccessTokenGenerator();
        generator.setAccessTokenCustomizer(new TokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(generator, new OAuth2AccessTokenGenerator());
    }

    @Bean
    public CorsFilter corsFilter(AuthorizationServerProperties authorizationServerProperties) {
        CorsConfiguration configuration = new CorsConfiguration();
        authorizationServerProperties.getCorsUrls().forEach(configuration::addAllowedOrigin);
        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }
}

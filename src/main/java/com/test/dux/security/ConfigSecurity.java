package com.test.dux.security;
import com.test.dux.security.custom.AccessDeniedResponse;
import com.test.dux.security.custom.AuthEntryPointResponse;
import com.test.dux.security.custom.UserDetailServiceCustom;
import com.test.dux.security.jwt.JwtFilter;
import com.test.dux.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {
    @Value("${path.permit.all}")
    private String[] pathPermitAll;
    @Value("${path.permit.admin}")
    private String[] pathRoleAdmin;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailServiceCustom userDetailServiceCustom, JwtUtils jwtUtils) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(jwtUtils, userDetailServiceCustom);
        AuthenticationEntryPoint authenticationEntryPoint = new AuthEntryPointResponse();
        AccessDeniedResponse accessDeniedResponse = new AccessDeniedResponse();

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.
                        requestMatchers(pathPermitAll).permitAll()
                        .requestMatchers(pathRoleAdmin).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedResponse))

                .addFilterAfter(jwtFilter, LogoutFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailServiceCustom userDetailServiceCustom) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailServiceCustom);
        PasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        daoAuthenticationProvider.setPasswordEncoder(noOpPasswordEncoder);
        return new ProviderManager(daoAuthenticationProvider);

    }

}

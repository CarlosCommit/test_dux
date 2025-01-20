package com.test.dux.security;
import com.test.dux.security.custom.UserDetailServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public AuthenticationManager authenticationManager(UserDetailServiceCustom userDetailServiceCustom) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailServiceCustom);
        PasswordEncoder noOpPasswordEncoder = NoOpPasswordEncoder.getInstance();
        daoAuthenticationProvider.setPasswordEncoder(noOpPasswordEncoder);
        return new ProviderManager(daoAuthenticationProvider);

    }

}

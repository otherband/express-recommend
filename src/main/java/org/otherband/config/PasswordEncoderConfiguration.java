package org.otherband.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8;

@Configuration
public class PasswordEncoderConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return defaultsForSpringSecurity_v5_8();
    }
}

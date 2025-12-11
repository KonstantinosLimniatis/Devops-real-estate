package gr.hua.dit.ds.ds_2025.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Μέθοδος για την κρυπτογράφηση του password
        return new BCryptPasswordEncoder();
    }
}
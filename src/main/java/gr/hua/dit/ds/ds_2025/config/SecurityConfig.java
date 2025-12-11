package gr.hua.dit.ds.ds_2025.config;

import gr.hua.dit.ds.ds_2025.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private UserService userService; // Ένα αντικείμενο τύπου UserService

    private UserDetailsService userDetailsService; // Ένα αντικείμενο τύπου UserDetailsService

    private BCryptPasswordEncoder passwordEncoder; // Ένα αντικείμενο τύπου BCryptPasswordEncoder για την κρυπτογράφηση του password

    public SecurityConfig(UserService userService, UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) { // Constructor της κλάσης
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Μέθοδος για την ασφάλεια της εφαρμογής
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/register", "/saveUser", "/images/**", "/js/**", "/css/**").permitAll() // Αυτά τα templates εμφανίζονται σε όλους τους χρήστες χωρίς να χρειαστεί authentication
                        .anyRequest().authenticated() // Οτιδήποτε άλλο χρειάζεται authentication
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Το template για το login
                        .defaultSuccessUrl("/realestate", true) // Η σελίδα μετά το login
                        .permitAll()) // Επιτρέπεται η είσοδος στη σελίδα του login σε όλους
                .logout((logout) -> logout.permitAll()); // Η επιλογή logout είναι διαθέσιμη σε όλους
        return http.build();
    }


}

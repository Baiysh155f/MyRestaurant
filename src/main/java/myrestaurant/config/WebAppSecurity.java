package myrestaurant.config;

import lombok.RequiredArgsConstructor;
import myrestaurant.exceptions.handler.GlobalExceptionHandler;
import myrestaurant.repository.EmployeesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.NoSuchElementException;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class WebAppSecurity {
    private final EmployeesRepository employeesRepository;

    public WebAppSecurity(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> employeesRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("email with = " + email + "not found "));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
    @Bean
    public HandlerExceptionResolver accessDeniedHandler() {
        return new GlobalExceptionHandler();
    }
}

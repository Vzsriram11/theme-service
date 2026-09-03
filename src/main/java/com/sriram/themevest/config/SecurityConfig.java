package com.sriram.themevest.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

@Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception
    {
        //Configure http security here
        httpSecurity.
                authorizeHttpRequests(
                        auth -> auth
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers(HttpMethod.GET,"/themes","/themes/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/themes").hasRole("ADMIN")
                                .anyRequest().authenticated()

                );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder)
    {
        // Create viewer and admin here

        UserDetails admin = User.withUsername("admin").password
                (passwordEncoder.encode("abc124")).roles("ADMIN").build();
        UserDetails viewer = User.withUsername("viewer").password
                (passwordEncoder.encode("abc123")).roles("USER").build();

        return new InMemoryUserDetailsManager(viewer,admin);
    }
}

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityConfig {

@Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity,JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception
    {
        //Configure http security here
        httpSecurity.
                authorizeHttpRequests(
                        auth -> auth
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers(HttpMethod.GET,"/themes","/themes/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/themes").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/auth/token").permitAll()
                                .anyRequest().authenticated()

                );
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //Removed HTTP Basic since JWT auth is supported.
       // httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Added JWT support
        httpSecurity.oauth2ResourceServer(
                oauth2 ->
                        oauth2.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
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

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter);

        return authenticationConverter;
    }
}

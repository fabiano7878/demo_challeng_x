package br.com.demo.person.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // permite h2-console rode local e acesse seus frames, com as urls diferentes e com complementos.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/components/**", "/js_origem/**", "/images/**","/node_modules/**").permitAll() // permite o acesso aos diretórios de estaticos
                        .requestMatchers("/h2-console/**").permitAll() // permite o acesso ao h2-console
                        .requestMatchers(HttpMethod.GET, "/person/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/person").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/person").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/person").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/person").hasRole("SYSTEM")
                        .requestMatchers(HttpMethod.DELETE, "/person").hasRole("SYSTEM")
                        .requestMatchers(HttpMethod.PATCH, "/person").hasRole("SYSTEM")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManagement(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


package com.example.routing.security;


import com.example.routing.repositories.ServiceUserRepository;
import com.example.routing.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class BasicSecurityConfiguration {
    CustomAuthenticationManager customAuthenticationManager;
    ServiceUserRepository serviceUserRepository;

    Environment env;
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        var AuthFilter = new AuthenticationFilter(customAuthenticationManager,env);
        AuthFilter.setFilterProcessesUrl("/api/route/token");

        return  http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(mgt->mgt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           //     .authorizeHttpRequests(req->req.requestMatchers(HttpMethod.POST, "/api/merchant/signup").permitAll())

                .addFilterBefore(new ExceptionErrorHandler(), AuthenticationFilter.class)
                .addFilter(AuthFilter)
                .addFilterAfter(new JwtFilter(serviceUserRepository, env), AuthenticationFilter.class)
                .build();

    }
}
package com.example.routing.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.routing.repositories.ServiceUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    ServiceUserRepository serviceUserRepository;

    Environment env;

    // List of URLs to exclude from JWT filtering
    private static final List<String> EXCLUDED_URLS = Arrays.asList(
            "/api/merchant/signup",
            "/api/merchant/test"
    );
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (!bearerToken.startsWith("Bearer")) {
//            filterChain.doFilter(request, response);
            throw new  NullPointerException("Please supply a token");
        }
        String token = bearerToken.replace("Bearer ", "");
        //decode token with secrete
        var user = JWT.require(Algorithm.HMAC512(env.getProperty("HMACSECRETE")))
                .build().verify(token).getSubject();
        var userDb = serviceUserRepository.findByUserId(user);
        if (userDb.isEmpty()) {
            throw new BadCredentialsException("");
        }
        var currUser = userDb.get();

        Authentication authentication = new UsernamePasswordAuthenticationToken(currUser.getUserId(), "", Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return EXCLUDED_URLS.stream().anyMatch(excludedUrl -> requestURI.matches(excludedUrl));
    }

}
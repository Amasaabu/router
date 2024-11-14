package com.example.routing.security.manager;


import com.example.routing.repositories.ServiceUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
//
@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    ServiceUserRepository serviceUserRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var userFromDb = serviceUserRepository.findByUserId(authentication.getName());
        if (userFromDb.isEmpty()) {
            throw new BadCredentialsException("");
        }
        var password = userFromDb.get().getPassword();
        if (!password.equals(authentication.getCredentials())) {
            throw new BadCredentialsException("Password wrong");
        }
//        if (!Utils.passwordEncoder().matches(authentication.getCredentials().toString(),user.getPassword())) {
//            throw new BadCredentialsException("Password wrong");
//        }
        //ensure credentials is not pased arround, pass null
        return new UsernamePasswordAuthenticationToken(userFromDb.get().getUserId(), password);
    }
}

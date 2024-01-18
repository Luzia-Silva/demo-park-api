package com.girludev.demoparkapi.web.controller.auth;

import com.girludev.demoparkapi.jwt.JwtToken;
import com.girludev.demoparkapi.jwt.JwtUserDetailsService;
import com.girludev.demoparkapi.web.dto.user.UserAuthDto;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserAuthDto userAuthDto, HttpServletRequest httpServletRequest){
        log.info("Process authentication by auth user", userAuthDto.getUsername());
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(userAuthDto.getUsername());
            return ResponseEntity.ok().body(token);
        }catch (AuthenticationException error){
            log.warn("Bad credential from username '{}' ", userAuthDto.getUsername());
        }
        return ResponseEntity.badRequest().body( new ErrorMessage(httpServletRequest, HttpStatus.BAD_REQUEST, "Invalid credential"));
    }
}

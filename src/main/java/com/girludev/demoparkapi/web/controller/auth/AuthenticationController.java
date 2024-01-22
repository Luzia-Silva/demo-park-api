package com.girludev.demoparkapi.web.controller.auth;

import com.girludev.demoparkapi.jwt.JwtToken;
import com.girludev.demoparkapi.jwt.JwtUserDetailsService;
import com.girludev.demoparkapi.web.dto.user.UserAuthDto;
import com.girludev.demoparkapi.web.dto.user.UserResponseDTO;
import com.girludev.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Authetication", description = "The Api is auth to user authetication with token JWT")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Auth user", description="Resource auth user",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Resource to successfully auth user with token in bearer.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid credential.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "invalid fields.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserAuthDto userAuthDto, HttpServletRequest request){
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
        return ResponseEntity.badRequest().body( new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credential"));
    }
}

package com.gabriel.taskManager.Controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.taskManager.DTOs.LoginRequestDTO;
import com.gabriel.taskManager.DTOs.LoginResponseDTO;
import com.gabriel.taskManager.Model.Role;
import com.gabriel.taskManager.Repository.UserRepository;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {

       var user = userRepository.findByUsername(loginRequestDTO.username());

       if(user.isEmpty() || !user.get().isLoginCorrect(loginRequestDTO, passwordEncoder)) {
        throw new BadCredentialsException("Invalid username or password");
       }

       var now = Instant.now();
       var expiresIn = 3600L;

       var scopes = user.get().getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.joining(" "));
    

       var claims = JwtClaimsSet.builder()
            .issuer("mybackend")
            .subject(user.get().getUserId().toString())
            .expiresAt(now.plusSeconds(expiresIn))
            .claim("scope", scopes)
            .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return ResponseEntity.ok(new LoginResponseDTO(jwtValue, expiresIn));
    }

}

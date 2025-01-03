package example.auth.service;

import example.auth.dto.AuthenticationResponse;
import example.auth.dto.LoginRequest;
import example.auth.dto.RegisterRequest;
import example.auth.model.Authority;
import example.auth.model.User;
import example.auth.security.JwtProvider;
import example.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(true);

        Authority authority = new Authority("ROLE_USER");
        user.addAuthority(authority);

        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInSec()))
                .username(loginRequest.getUsername())
                .build();
    }
}

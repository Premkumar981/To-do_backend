package com.hcl.To_do_backend.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hcl.To_do_backend.dto.LoginRequest;
import com.hcl.To_do_backend.dto.SignupRequest;
import com.hcl.To_do_backend.entity.User;
import com.hcl.To_do_backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;


import java.util.List;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- SIGNUP ----------
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider("LOCAL")
                .build();

        userRepository.save(user);
        return "Signup successful";
    }

    // -------- NORMAL LOGIN --------
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(() -> "ROLE_USER")
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        // 🔥 THIS IS THE MISSING PIECE
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        SecurityContextHolder.setContext(context);

        return "Login successful";
    }



    // -------- GOOGLE LOGIN --------
    @PostMapping("/google")
    public String googleLogin(@RequestBody String token, HttpSession session) throws Exception {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance()
        )
                .setAudience(List.of("492798288378-80djc6sg7quo3ij93ealcdr2ia1jdlmg.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            throw new RuntimeException("Invalid Google token");
        }

        var payload = idToken.getPayload();
        String email = payload.getEmail();
        String name = (String) payload.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .email(email)
                                        .name(name)
                                        .provider("GOOGLE")
                                        .build()
                        )
                );

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(() -> "ROLE_USER")
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        // 🔥 SAME FIX
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        SecurityContextHolder.setContext(context);

        return "Google login successful";
    }

    @GetMapping("/me")
    public User getCurrentUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



    // ---------- LOGOUT ----------
    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}

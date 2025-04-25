package com.example.Kaushik.My.Day.Controller;
import com.example.Kaushik.My.Day.DTO.LoginRequest;
import com.example.Kaushik.My.Day.DTO.RegisterRequest;
import com.example.Kaushik.My.Day.Model.Users;
import com.example.Kaushik.My.Day.Repository.UserRepository;
import com.example.Kaushik.My.Day.Security.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        Users temp = userRepository.findByEmail(request.email);
        if (temp !=null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        Users user = new Users();
        user.setEmail(request.email);
        user.setPassword(passwordEncoder.encode(request.password));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Users user = userRepository.findByEmail(request.email);
        if (user == null || !passwordEncoder.matches(request.password, user.getPassword())) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getUserId());

        response.setHeader("Authorization", "Bearer " + token);

        return ResponseEntity.ok(java.util.Map.of(
                "message", "Login successful",
                "token", token
        ));
    }

}

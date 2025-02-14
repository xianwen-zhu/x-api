package com.kevin.zee.x_api.controller;

import com.kevin.zee.x_api.dto.ApiResponse;
import com.kevin.zee.x_api.model.User;
import com.kevin.zee.x_api.repository.UserRepository;
import com.kevin.zee.x_api.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "User already exists", null));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse<>(200, "User registered successfully", null));
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", token);
            responseData.put("expires_in", 86400); // 1 天（秒）
            responseData.put("refresh_token", refreshToken);

            return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", responseData));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(401, "Invalid username or password", null));
        }
    }
}
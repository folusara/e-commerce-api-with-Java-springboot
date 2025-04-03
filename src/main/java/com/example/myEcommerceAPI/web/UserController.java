package com.example.myEcommerceAPI.web;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myEcommerceAPI.DataTransferObjects.ResetPasswordRequest;
import com.example.myEcommerceAPI.entity.User;
import com.example.myEcommerceAPI.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

import com.example.myEcommerceAPI.repository.UserRepository;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "Auth", description = "API for managing Auth")
public class UserController {


	UserService userService;
	UserRepository userRepository;
	private final Set<String> tokenBlacklist = new HashSet<>();

	@Operation(summary = "get user details")
	@GetMapping("/{id}")
	public ResponseEntity<String> findById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUser(id).getUsername(),HttpStatus.OK);
	}

	@Operation(summary = "Register user")
    @PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		
		Optional <User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@Operation(summary = "reset password")
	@PutMapping("/reset-password/{id}")
	public ResponseEntity<?> ResetPassword(@PathVariable Long id, @Valid @RequestBody ResetPasswordRequest request) {
		System.err.println(id);
		System.err.println(request);
		// return;
		userService.ResetPassword(id, request.getNewPassword());
		return new ResponseEntity<>("password reset successful!", HttpStatus.ACCEPTED);
	}

	@Operation(summary = "forgot password")
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> ForgotPassword(@RequestBody String entity) {
		System.err.println("hello forgot password");
		return new ResponseEntity<>("Password reset process started", HttpStatus.ACCEPTED);
	}

	@Operation(summary = "Log-out")
	 @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklist.add(token);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

}

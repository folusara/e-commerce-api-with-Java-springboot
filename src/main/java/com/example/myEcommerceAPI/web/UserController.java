package com.example.myEcommerceAPI.web;


import java.util.Optional;

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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

import com.example.myEcommerceAPI.repository.UserRepository;


@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


	UserService userService;
	UserRepository userRepository;

	@GetMapping("/{id}")
	public ResponseEntity<String> findById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUser(id).getUsername(),HttpStatus.OK);
	}

    @PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		
		Optional <User> existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@PutMapping("/reset-password/{id}")
	public ResponseEntity<?> ResetPassword(@PathVariable Long id, @Valid @RequestBody ResetPasswordRequest request) {
		System.err.println(id);
		System.err.println(request);
		// return;
		userService.ResetPassword(id, request.getNewPassword());
		return new ResponseEntity<>("password reset successful!", HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<?> ForgotPassword(@RequestBody String entity) {
		System.err.println("hello forgot password");
		return new ResponseEntity<>("Password reset process started", HttpStatus.ACCEPTED);
	}

}

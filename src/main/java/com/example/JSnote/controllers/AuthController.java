package com.example.JSnote.controllers;

import com.example.JSnote.config.UserAuthenticationProvider;
import com.example.JSnote.dtos.SignUpDto;
import com.example.JSnote.dtos.UserDto;
import com.example.JSnote.dtos.UserUpdateDto;
import com.example.JSnote.services.UserService;
import com.example.JSnote.dtos.CredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>>  login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));

        Map<String, Object> response = new HashMap<>();
        response.put("token", userDto.getToken());
        response.put("user", userDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (userAuthenticationProvider.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body(null); // Refresh token истек
        }

        String username = userAuthenticationProvider.validateToken(refreshToken).getName();
        String newAccessToken = userAuthenticationProvider.createToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", refreshToken); // Можно сгенерировать новый refresh token, если требуется
        return ResponseEntity.ok(tokens);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
        UserDto updatedUser = userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

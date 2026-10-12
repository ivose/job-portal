package com.zosh.job.controller;

import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.mapper.UserMapper;
import com.zosh.job.model.User;
import com.zosh.job.payload.UpdateUserRequest;
import com.zosh.job.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(
            @RequestHeader("X-User-Email") String email
    ) throws Exception {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody @Valid UpdateUserRequest req) throws Exception {
        return ResponseEntity.ok(userService.updateProfile(email, req));
    }

    @PutMapping("/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(
            @PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(userService.suspendUser(userId));
    }

    @PutMapping("/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(
            @PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserByID(
            @PathVariable Long userId) throws Exception {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() throws Exception {
        return ResponseEntity.ok(UserMapper.toDTOList(userService.getAllUsers()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}

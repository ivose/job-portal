package com.zosh.job.service;

import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.model.User;
import com.zosh.job.payload.UpdateUserRequest;
import java.util.List;

public interface UserService {
  User getUserByEmail(String email) throws Exception;

  User getUserById(Long id) throws Exception;

  List<User> getAllUsers();

  UserResponse updateProfile(String email, UpdateUserRequest req) throws Exception;

  UserResponse suspendUser(Long id) throws Exception;

  UserResponse activateUser(Long id) throws Exception;

  UserResponse deleteUser(Long id) throws Exception;
}

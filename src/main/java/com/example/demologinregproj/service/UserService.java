package com.example.demologinregproj.service;

import com.example.demologinregproj.dto.UserDto;
import com.example.demologinregproj.entity.User;

import java.util.List;

public interface UserService {

    void saveMyUser(UserDto userDto);

    User findUserByEmailId(String email);

    List<UserDto> findAllUsers();
}

package com.klishch.diploma.services;

import com.klishch.diploma.dto.UserDto;
import com.klishch.diploma.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User createAndSaveUser(UserDto userDto);
}
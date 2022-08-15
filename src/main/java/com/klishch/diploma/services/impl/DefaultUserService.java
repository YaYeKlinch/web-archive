package com.klishch.diploma.services.impl;

import com.klishch.diploma.dto.UserDto;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.entities.enums.Role;
import com.klishch.diploma.exceptions.UserAlreadyExistException;
import com.klishch.diploma.repositories.UserRepository;
import com.klishch.diploma.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@AllArgsConstructor
public class DefaultUserService implements  UserDetailsService, UserService {

    private static final Logger logger = LogManager.getLogger(DefaultUserService.class);

    UserRepository userRepository;

    private  PasswordEncoder passwordEncoder;



    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("trying to find user with username " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User createAndSaveUser(UserDto userDto) {
        logger.info("trying to register user with username " + userDto.getEmail());
        if (emailExists(userDto.getEmail())) {
            logger.info("tried to register user with username " + userDto.getEmail() +
                    ", throwing UserAlreadyExistException");
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }
        User userToCreate = new User();
        userToCreate.setFirstName(userDto.getFirstName());
        userToCreate.setLastName(userDto.getLastName());
        userToCreate.setActive(true);
        userToCreate.setPassword(encodePassword(userDto.getPassword()));
        userToCreate.setUsername(userDto.getEmail());
        userToCreate.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(userToCreate);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
    private boolean emailExists(String email){
        return userRepository.findByUsername(email) != null;
    }

}

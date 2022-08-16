package com.klishch.diploma.controllers;


import com.klishch.diploma.dto.UserDto;
import com.klishch.diploma.exceptions.UserAlreadyExistException;
import com.klishch.diploma.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.apache.logging.log4j.Logger;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
public class RegistrationController {
    private static final Logger logger = (Logger) LogManager.getLogger(RegistrationController.class);

    UserService userService;

    @GetMapping("/registration")
    public String registration(Model model ){
        logger.debug("requested /registration get method");
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        logger.debug("returning registration.html to user");
        return "registration";
    }
    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model) {
        if(bindingResult.hasErrors()){
            logger.debug("userDto has errors , returning to registration.html");
            return new ModelAndView("registration", "user", userDto);
        }
        try {
            userService.createAndSaveUser(userDto);
        } catch (UserAlreadyExistException ex) {
            logger.debug("user already exists , returning to registration.html");
            model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("registration", "user", userDto);
        }
        logger.debug("returning login.html to user");
        return new ModelAndView("login", "user", userDto);
    }
}
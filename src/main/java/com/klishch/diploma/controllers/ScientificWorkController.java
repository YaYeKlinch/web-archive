package com.klishch.diploma.controllers;

import com.klishch.diploma.dto.ScientificWorkDto;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ScientificWorkController {

    ScientificWorkService scientificWorkService;

    @GetMapping("/upload-work")
    public String getUploadWorkPage(Model model){
        ScientificWorkDto scientificWorkDto = new ScientificWorkDto();
        model.addAttribute("work", scientificWorkDto);
        return "uploadWork";
    }

    @PostMapping("/upload-work")
    public String uploadWork(@AuthenticationPrincipal User user,
                             @Valid ScientificWorkDto scientificWorkDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("work", scientificWorkDto);
            return "uploadWork";
        }
        scientificWorkService.createWork(scientificWorkDto, user);
        model.addAttribute("work", scientificWorkDto);
        return "redirect:/";
    }


}

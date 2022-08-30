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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
                             @RequestParam("file") MultipartFile file,
                             @Valid ScientificWorkDto scientificWorkDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors() || file.isEmpty()){
            model.addAttribute("work", scientificWorkDto);
            return "uploadWork";
        }
        scientificWorkService.createWork(scientificWorkDto, user, file);
        model.addAttribute("work", scientificWorkDto);
        return "redirect:/";
    }


}

package com.klishch.diploma.controllers;

import com.klishch.diploma.dto.ScientificWorkDto;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("/your-works")
    public String getUsersWorksPage (@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam(value = "sort", required = false) String sortBy,
                                     @RequestParam(value = "nameBy", required = false) String nameBy,
                                     @AuthenticationPrincipal User user,
                                     Model model){
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<ScientificWork> works = scientificWorkService.findScientificWorkByUser(page,size,sort,user);
        int totalPages = works.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("works", works);
        return "userWorks";
    }

    @GetMapping("/new-works")
    public String getAllNotPublishedPage(@RequestParam("page") Optional<Integer> page,
                                         @RequestParam("size") Optional<Integer> size,
                                         @RequestParam(value = "sort", required = false) String sortBy,
                                         @RequestParam(value = "nameBy", required = false) String nameBy,
                                         Model model) {
        Sort sort = ControllerUtils.getSort(sortBy, nameBy, model);
        Page<ScientificWork> works = scientificWorkService.findScientificWorkByPublishing(page, size, sort, false);
        int totalPages = works.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages, model);
        model.addAttribute("works", works);
        return "newWorks";
    }

    @GetMapping("/new-works/publish-work/{work}")
    public String publishWork(@PathVariable("work") ScientificWork scientificWork){
        scientificWorkService.changeScientificWorkPublishStatus(scientificWork);
        return scientificWork.isPublished() ? "redirect:/new-works" : "redirect:/published-works";
    }

    @GetMapping("/published-works")
    public String getAllPublishedWorksPage(@RequestParam("page") Optional<Integer> page,
                                           @RequestParam("size") Optional<Integer> size,
                                           @RequestParam(value = "sort", required = false) String sortBy,
                                           @RequestParam(value = "nameBy", required = false) String nameBy,
                                           @RequestParam(value = "title", required = false) String title,
                                           Model model){
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        String actualTitle = ControllerUtils.getStringField(title);
        model.addAttribute("title", actualTitle);
        Page<ScientificWork> works = chooseFindWorkMethod(actualTitle, page, size, sort);
        int totalPages = works.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("works", works);
        return "publishedWorks";
    }
    private Page<ScientificWork> chooseFindWorkMethod(String title,
                                                      Optional<Integer> page, Optional<Integer> size, Sort sort) {
        return title.isBlank()  ?
                scientificWorkService.findScientificWorkByPublishing(page, size, sort, true) :
                scientificWorkService.findScientificWorksByTitle(page, size, sort, title);
    }
}

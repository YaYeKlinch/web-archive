package com.klishch.diploma.controllers;

import com.klishch.diploma.dto.ReviewDto;
import com.klishch.diploma.dto.ScientificWorkDto;
import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.services.ReviewService;
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

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ReviewController {

    ReviewService reviewService;
    ScientificWorkService scientificWorkService;

    @GetMapping("/published-works/{work}")
    public String getWorkPage(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(value = "sort", required = false) String sortBy,
                              @RequestParam(value = "nameBy", required = false) String nameBy,
                              @PathVariable("work") ScientificWork scientificWork,
                              Model model){
        model.addAttribute("work",scientificWork);
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<Review> reviews = reviewService.findReviewsByWork(page, size, sort, scientificWork);
        int totalPages = reviews.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("reviews", reviews);
        return "workReviews";
    }

    @GetMapping("/published-works/{work}/create-review")
    public String getCreateReviewPage(@PathVariable("work") ScientificWork scientificWork,
                                      Model model){
        model.addAttribute("work",scientificWork.getId());
        ReviewDto reviewDto = new ReviewDto();
        model.addAttribute("review", reviewDto);
        return "createReview";
    }

    @PostMapping("/published-works/{work}/create-review")
    public String uploadWork(@AuthenticationPrincipal User user,
                             @PathVariable("work") ScientificWork scientificWork,
                             @Valid ReviewDto reviewDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("work",scientificWork.getId());
            model.addAttribute("review", reviewDto);
            return "createReview";
        }
        reviewService.createReview(reviewDto,user,scientificWork);
        model.addAttribute("review", reviewDto);
        return "redirect:/published-works/{work}";
    }
}

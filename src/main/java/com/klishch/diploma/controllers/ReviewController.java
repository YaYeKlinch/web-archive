package com.klishch.diploma.controllers;

import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.services.ReviewService;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
}

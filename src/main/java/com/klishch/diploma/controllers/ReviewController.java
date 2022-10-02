package com.klishch.diploma.controllers;

import com.klishch.diploma.dto.ReviewDto;
import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.services.FileSystemService;
import com.klishch.diploma.services.ReviewService;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ReviewController {

    ReviewService reviewService;
    ScientificWorkService scientificWorkService;
    FileSystemService fileSystemService;

    @GetMapping("/published-works/{work}")
    public String getWorkPage(@AuthenticationPrincipal User user,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(value = "sort", required = false) String sortBy,
                              @RequestParam(value = "nameBy", required = false) String nameBy,
                              @PathVariable("work") ScientificWork scientificWork,
                              Model model){
        model.addAttribute("work",scientificWork);
        model.addAttribute("userId", user.getId());
        model.addAttribute("reviewCreated", isUserCreatedReviewForWork(user,scientificWork));
        model.addAttribute("file", getFileName(scientificWork));
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<Review> reviews = reviewService.findReviewsByWorkPageable(page, size, sort, scientificWork);
        int totalPages = reviews.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("reviews", reviews);
        return "workReviews";
    }

    @GetMapping("/published-works/{work}/create-review")
    public String getCreateReviewPage(@AuthenticationPrincipal User user,
                                      @PathVariable("work") ScientificWork scientificWork,
                                      Model model){
        model.addAttribute("work",scientificWork.getId());
        ReviewDto reviewDto = new ReviewDto();
        model.addAttribute("review", reviewDto);
        return isUserCreatedReviewForWork(user, scientificWork) ? "redirect:/published-works/{work}" : "createReview";
    }

    @PostMapping("/published-works/{work}/create-review")
    public String createReview(@AuthenticationPrincipal User user,
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
        return "redirect:/published-works/{work}";
    }

    @PreAuthorize("#review.getUser().getUsername() == authentication.principal.username")
    @GetMapping("/published-works/{work}/update-review/{review}")
    public String getUpdateReviewPage(@PathVariable("work") ScientificWork scientificWork,
                                      @PathVariable("review") Review review,
                                      Model model){
        model.addAttribute("work",scientificWork.getId());
        ReviewDto reviewDto = new ReviewDto(review.getText(), review.getRate());
        model.addAttribute("review", reviewDto);
        model.addAttribute("reviewId", review.getId());
        return "updateReview";
    }

    @PreAuthorize("#review.getUser().getUsername() == authentication.principal.username")
    @PostMapping("/published-works/{work}/update-review/{review}")
    public String updateReview(@PathVariable("work") ScientificWork scientificWork,
                             @PathVariable("review") Review review,
                             @Valid ReviewDto reviewDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("work",scientificWork.getId());
            model.addAttribute("review", reviewDto);
            model.addAttribute("reviewId", review.getId());
            return "updateReview";
        }
        reviewService.updateReview(reviewDto, review);
        return "redirect:/published-works/{work}";
    }

    @PreAuthorize("#review.getUser().getUsername() == authentication.principal.username")
    @GetMapping("/published-works/{work}/delete-review/{review}")
    public String deleteReview(@PathVariable("review") Review review){
        reviewService.deleteReview(review);
        return "redirect:/published-works/{work}";
    }

    @GetMapping("/published-works/{work}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileSystemService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private boolean isUserCreatedReviewForWork(User user, ScientificWork work){
        return reviewService.findReviewsByWork(work).stream()
                .anyMatch(review -> review.getUser().getId().equals(user.getId()));
    }

    private String getFileName(ScientificWork scientificWork){
            return Paths.get(scientificWork.getFilePath()).getFileName().toString();
    }
}

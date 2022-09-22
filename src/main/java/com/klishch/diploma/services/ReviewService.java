package com.klishch.diploma.services;

import com.klishch.diploma.dto.ReviewDto;
import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review createReview(ReviewDto reviewDto, User user, ScientificWork scientificWork);

    Review updateReview(ReviewDto reviewDto, Review review);

    void deleteReview(Review review);

    Page<Review> findReviewsByWorkPageable(Optional<Integer> page, Optional<Integer> size,
                                           Sort sort, ScientificWork scientificWork);
    List<Review> findReviewsByWork(ScientificWork scientificWork);
}

package com.klishch.diploma.services.impl;

import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.repositories.ReviewRepository;
import com.klishch.diploma.services.ReviewService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class DefaultReviewServiceTest {

    final static long WORK_ID = 1;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewService reviewService;
    @Mock
    Review review;
    @Mock
    ScientificWork scientificWork;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void shouldDeleteReview(){
        reviewService.deleteReview(review);
        verify(reviewRepository).delete(review);
    }

    @Test
    public void shouldFindReviewsByWork(){
        reviewService.findReviewsByWork(scientificWork);
        verify(reviewRepository).findReviewsByScientificWorkId(WORK_ID);
    }
}
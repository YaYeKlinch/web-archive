package com.klishch.diploma.services.impl;

import com.klishch.diploma.dto.ReviewDto;
import com.klishch.diploma.entities.Review;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.repositories.ReviewRepository;
import com.klishch.diploma.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.klishch.diploma.constants.PaginationConstants.DEFAULT_PAGE_NUMBER;
import static com.klishch.diploma.constants.PaginationConstants.DEFAULT_SIZE;

@AllArgsConstructor
@Service
public class DefaultReviewService implements ReviewService {

    public static final String MAIL_FROM = "noreply@gmail.com";

    ReviewRepository reviewRepository;
    private final JavaMailSender mailSender;

    @Override
    public Review createReview(ReviewDto reviewDto, User user, ScientificWork scientificWork) {
        Review review = new Review();
        review.setDate(LocalDateTime.now());
        review.setRate(reviewDto.getRate());
        review.setText(reviewDto.getText());
        review.setUser(user);
        review.setWork(scientificWork);
        sendMail(scientificWork.getUser().getUsername(), MessageFormat.format("User {0} left review for your work {1}",
                user.getUsername(),
                scientificWork.getTitle()));
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(ReviewDto reviewDto, Review review) {
        review.setText(reviewDto.getText());
        review.setRate(reviewDto.getRate());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Page<Review> findReviewsByWorkPageable(Optional<Integer> page, Optional<Integer> size,
                                                  Sort sort, ScientificWork scientificWork) {
        return reviewRepository.findReviewsByScientificWorkIdPageable(createPageRequest(page, size, sort),
                scientificWork.getId());
    }

    @Override
    public List<Review> findReviewsByWork(ScientificWork scientificWork) {
        return reviewRepository.findReviewsByScientificWorkId(scientificWork.getId());
    }

    private PageRequest createPageRequest(Optional<Integer> page, Optional<Integer> size, Sort sort) {
        return sort == null ?
                PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE)) :
                PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE), sort);
    }

    private void sendMail(String emailTo, String messageText){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(MAIL_FROM);
        message.setTo(emailTo);
        message.setSubject("New review for your scientific work!");
        message.setText(messageText);

        mailSender.send(message);
    }
}

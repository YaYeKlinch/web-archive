package com.klishch.diploma.repositories;

import com.klishch.diploma.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review,Long> {

    @Query("SELECT r FROM Review AS r WHERE r.work.id = ?1")
    Page<Review> findReviewsByScientificWorkIdPageable(Pageable pageable, @Param("scientific_work_id") Long workId);

    @Query("SELECT r FROM Review AS r WHERE r.work.id = ?1")
    List<Review> findReviewsByScientificWorkId(@Param("scientific_work_id") Long workId);
}


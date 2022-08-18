package com.klishch.diploma.repositories;

import com.klishch.diploma.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends PagingAndSortingRepository<Review,Long> {

    @Query("SELECT r FROM Review AS r WHERE r.work = ?1")
    Page<Review> findReviewsByScientificWorkId(Pageable pageable, @Param("scientific_work_id") Long workId);
}

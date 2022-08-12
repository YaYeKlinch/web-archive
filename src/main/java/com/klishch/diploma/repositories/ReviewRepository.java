package com.klishch.diploma.repositories;

import com.klishch.diploma.entities.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends PagingAndSortingRepository<Review,Long> {

}

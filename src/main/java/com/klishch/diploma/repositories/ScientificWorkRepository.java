package com.klishch.diploma.repositories;

import com.klishch.diploma.entities.ScientificWork;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface ScientificWorkRepository extends PagingAndSortingRepository<ScientificWork,Long> {

    Page<ScientificWork> findScientificWorksByIsPublished(Pageable pageable, @Param("is_published") boolean isPublished);

    Page<ScientificWork> findScientificWorksByTitle(Pageable pageable, @Param("title") String title);

    Page<ScientificWork> findScientificWorksByUserLastNameContaining(Pageable pageable, String lastName);

    Page<ScientificWork> findScientificWorksByUser_id(Pageable pageable, @Param("user_id") Long userId);

}

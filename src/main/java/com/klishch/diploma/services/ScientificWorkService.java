package com.klishch.diploma.services;


import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface ScientificWorkService {

    Page<ScientificWork> findScientificWorksByTitle(Optional<Integer> page, Optional<Integer> size,
                                                    Sort sort, String title);

    Page<ScientificWork> findScientificWorkByPublishing(Optional<Integer> page, Optional<Integer> size,
                                                        Sort sort, boolean isPublished);

    Page<ScientificWork> findScientificWorkByUser(Optional<Integer> page, Optional<Integer> size,
                                                  Sort sort, User user);

    Page<ScientificWork> findScientificWorkByUsersLastName(Optional<Integer> page, Optional<Integer> size,
                                                           Sort sort, User user);
}
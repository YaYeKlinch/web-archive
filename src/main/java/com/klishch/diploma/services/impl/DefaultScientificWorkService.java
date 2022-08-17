package com.klishch.diploma.services.impl;


import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.repositories.ScientificWorkRepository;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultScientificWorkService implements ScientificWorkService {

    private final static int DEFAULT_PAGE_NUMBER = 1;
    private final static int DEFAULT_SIZE = 5;

    ScientificWorkRepository scientificWorkRepository;

    @Override
    public Page<ScientificWork> findScientificWorksByTitle(Optional<Integer> page, Optional<Integer> size,
                                                           Sort sort, String title) {
        return scientificWorkRepository.findScientificWorksByTitle(createPageRequest(page, size, sort), title);
    }

    @Override
    public Page<ScientificWork> findScientificWorkByPublishing(Optional<Integer> page, Optional<Integer> size,
                                                               Sort sort, boolean isPublished) {
        return scientificWorkRepository.findScientificWorksByIsPublished(createPageRequest(page, size, sort),
                isPublished);
    }

    @Override
    public Page<ScientificWork> findScientificWorkByUser(Optional<Integer> page, Optional<Integer> size,
                                                         Sort sort, User user) {
        return scientificWorkRepository.findScientificWorksByUser_id(createPageRequest(page, size, sort), user.getId());
    }

    @Override
    public Page<ScientificWork> findScientificWorkByUsersLastName(Optional<Integer> page, Optional<Integer> size,
                                                                  Sort sort, User user) {
        return scientificWorkRepository.findScientificWorksByUserLastNameContaining(createPageRequest(page, size, sort),
                user.getLastName());
    }

    private PageRequest createPageRequest(Optional<Integer> page, Optional<Integer> size, Sort sort) {
        return sort == null ? PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE)) :
                PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE), sort);
    }

}

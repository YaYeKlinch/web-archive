package com.klishch.diploma.services.impl;

import com.klishch.diploma.dto.ScientificWorkDto;
import com.klishch.diploma.entities.ScientificWork;
import com.klishch.diploma.entities.User;
import com.klishch.diploma.repositories.ScientificWorkRepository;
import com.klishch.diploma.services.FileSystemService;
import com.klishch.diploma.services.ScientificWorkService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.klishch.diploma.constants.PaginationConstants.DEFAULT_PAGE_NUMBER;
import static com.klishch.diploma.constants.PaginationConstants.DEFAULT_SIZE;

@Service
@AllArgsConstructor
public class DefaultScientificWorkService implements ScientificWorkService {

    ScientificWorkRepository scientificWorkRepository;
    FileSystemService fileSystemService;

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

    @Override
    public ScientificWork createWork(ScientificWorkDto scientificWorkDto, User user, MultipartFile file) {
        ScientificWork scientificWork = new ScientificWork();
        scientificWork.setTitle(scientificWorkDto.getTitle());
        scientificWork.setAnnotation(scientificWorkDto.getAnnotation());
        scientificWork.setPublished(false);
        scientificWork.setSendingDate(LocalDateTime.now());
        scientificWork.setUser(user);
        scientificWork.setFilePath(fileSystemService.store(file));
        return scientificWorkRepository.save(scientificWork);
    }

    private PageRequest createPageRequest(Optional<Integer> page, Optional<Integer> size, Sort sort) {
        return sort == null ?
                PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE)) :
                PageRequest.of(page.orElse(DEFAULT_PAGE_NUMBER) - 1, size.orElse(DEFAULT_SIZE), sort);
    }

}

package com.klishch.diploma.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {

    void store(MultipartFile file);

}

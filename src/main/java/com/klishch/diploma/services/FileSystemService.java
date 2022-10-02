package com.klishch.diploma.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import org.springframework.core.io.Resource;

public interface FileSystemService {

    void init();

    String store(MultipartFile file);

    Path load(String filename);

    Resource loadAsResource(String filename);
}

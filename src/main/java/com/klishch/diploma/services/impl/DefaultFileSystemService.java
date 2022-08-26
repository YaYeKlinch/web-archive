package com.klishch.diploma.services.impl;

import com.klishch.diploma.constants.StorageProperties;
import com.klishch.diploma.exceptions.UploadFileException;
import com.klishch.diploma.services.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DefaultFileSystemService implements FileSystemService {

    private final Path root;

    @Autowired
    public DefaultFileSystemService(StorageProperties properties) {
        this.root = Paths.get(properties.getLocation());
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new UploadFileException("Failed to store empty file.");
            }
            Path destinationFile = this.root.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(root.toAbsolutePath())) {
                throw new UploadFileException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                return destinationFile.toString();
            }
        } catch (IOException e) {
            throw new UploadFileException("Failed to store file.", e);
        }

    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        }
        catch (IOException e) {
            throw new UploadFileException("Could not initialize storage", e);
        }
    }

}

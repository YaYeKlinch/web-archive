package com.klishch.diploma.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ScientificWorkDto {

    @NotNull
    private String title;

    @NotNull
    private String annotation;

    @NotNull
    private MultipartFile file;

    public ScientificWorkDto(){}

    public ScientificWorkDto(@NotNull String title, @NotNull String annotation, @NotNull MultipartFile file){
        this.title = title;
        this.annotation = annotation;
        this.file = file;
    }
}

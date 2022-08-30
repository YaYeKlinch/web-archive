package com.klishch.diploma.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ScientificWorkDto {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String annotation;

    public ScientificWorkDto(){}

    public ScientificWorkDto(@NotNull @NotBlank String title, @NotNull @NotBlank String annotation){
        this.title = title;
        this.annotation = annotation;
    }
}

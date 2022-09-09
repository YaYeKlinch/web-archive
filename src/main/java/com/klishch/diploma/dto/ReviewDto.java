package com.klishch.diploma.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ReviewDto {

    @NotBlank
    private String text;

    @Min(1)
    @Max(5)
    private int rate;

    public ReviewDto(){}

    public ReviewDto( @NotBlank String text, @Min(1) @Max(5) int rate){
        this.rate = rate;
        this.text = text;
    }
}

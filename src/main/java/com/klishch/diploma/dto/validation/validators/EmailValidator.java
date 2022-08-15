package com.klishch.diploma.dto.validation.validators;


import com.klishch.diploma.dto.validation.EmailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailValidation, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^(?d)(?=.{2,64}@)[\\w!#$%&'*+\\-\\/=?^`{|}~]+(\\.[\\w!#$%&'*+\\-\\/=?^`{|}~]+)*@[\\w\\-&&[^_]]{1,63}(\\.[\\w\\-&&[^_]]{1,63})+$";
    @Override
    public void initialize(EmailValidation constraintAnnotation) {
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateEmail(email));
    }
    private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
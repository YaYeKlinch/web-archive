package com.klishch.diploma.exceptions;

public class UploadFileException extends RuntimeException{

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(String message, Exception e){
        super(message,e);
    }
}

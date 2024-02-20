package com.mylibrary.libraryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)  // before it was 500, "Internal Server Error"
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private Long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
        super("%s not found with %s : %s".formatted(resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}

package com.learning.coursestudent.exception;

import org.springframework.http.ResponseEntity;

public class ApiExceptionHandler {

    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        // 1. Create payload containing exception details
        // 2. Return response Entity
        return null;
    }
}

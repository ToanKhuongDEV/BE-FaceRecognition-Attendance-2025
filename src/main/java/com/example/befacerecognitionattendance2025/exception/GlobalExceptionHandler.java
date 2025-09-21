package com.example.befacerecognitionattendance2025.exception;

import com.example.befacerecognitionattendance2025.base.RestData;
import com.example.befacerecognitionattendance2025.base.VsResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestData<?>> handleNotFoundException(NotFoundException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<RestData<?>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }


    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<RestData<?>> handleInvalidException(InvalidException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<RestData<?>> handleUploadFileException(UploadFileException ex) {
        return VsResponseUtil.error(ex.getStatus(), ex.getMessage());
    }
}

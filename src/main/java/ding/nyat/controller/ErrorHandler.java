package ding.nyat.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "ding.nyat")
public class ErrorHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("timestamp", (new Date()).toString());
        resp.put("status", 500);
        resp.put("error", "Internal Server Error");
        resp.put("message", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

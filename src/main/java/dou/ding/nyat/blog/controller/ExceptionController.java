package dou.ding.nyat.blog.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String error(Exception ex) {
        ex.printStackTrace();
        return "error/error";
    }
}

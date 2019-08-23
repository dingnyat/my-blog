package ding.nyat.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "ding.nyat.controller.client")
public class ErrorHandler {

    @ExceptionHandler({NoHandlerFoundException.class})
    public ModelAndView handleNotFoundException(NoHandlerFoundException ex) {
        System.out.println("not found");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Sorry, we couldn't find the page you were looking for.");
        modelAndView.addObject("errorCodeMessage", "Error 404, Not Found!");
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleServerException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorCodeMessage", "Error 500, Internal Server Error!");
        modelAndView.setViewName("error/error");
        return modelAndView;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException(AccessDeniedException ex) {
        return "error/access-denied";
    }

    @Controller
    public static class ErrorHandleController implements ErrorController {
        @GetMapping("/invalid-session")
        public String invalidSession() {
            return "error/invalid-session";
        }

        @GetMapping("/expired-session")
        public String expiredSession() {
            return "error/expired-session";
        }

        @ResponseStatus(HttpStatus.FORBIDDEN)
        @GetMapping("/access-denied")
        public String accessDenied() {
            return "error/access-denied";
        }

        @GetMapping("/error")
        public String hanleError(HttpServletRequest request) {
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            String message = "Sorry, Something went wrong!";
            String errorCodeMessage = "";
            if (status != null) {
                int statusCode = Integer.valueOf(status.toString());
                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    errorCodeMessage = "Error 404, Not Found!";
                    message = "Sorry, we couldn't find the page you were looking for.";
                } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                    errorCodeMessage = "Error 500, Internal Server Error!";
                } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                    errorCodeMessage = "Error 401, Unauthorized!";
                } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                    errorCodeMessage = "Error 400, Bad Request!";
                } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                    errorCodeMessage = "Error 403, Forbidden!";
                }
            }
            request.setAttribute("message", message);
            request.setAttribute("errorCodeMessage", errorCodeMessage);
            return "error/error";
        }

        @Override
        public String getErrorPath() {
            return "/error";
        }
    }
}

package dou.ding.nyat.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

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

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

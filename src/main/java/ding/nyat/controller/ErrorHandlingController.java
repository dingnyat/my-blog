package ding.nyat.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandlingController implements ErrorController {

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

    @GetMapping("/404")
    public String error404(HttpServletRequest request) {
        request.setAttribute("message", "Error 404, Not Found!");
        request.setAttribute("errorCodeMessage", "Sorry, we couldn't find the page you were looking for.");
        return "error/error";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
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

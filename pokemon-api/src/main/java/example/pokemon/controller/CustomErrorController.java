package example.pokemon.controller;

import example.pokemon.dto.ErrorInfo;
import example.pokemon.exception.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
public class CustomErrorController implements ErrorController {

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCardNotFoundException(CardNotFoundException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(requestUrl, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler(DuplicateCardException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateCardException(DuplicateCardException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(requestUrl, ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateStudentException(DuplicateStudentException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(requestUrl, ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorInfo);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleStudentNotFoundException(StudentNotFoundException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(requestUrl, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            String errorMessage = error.getMessage();
            errors.add(errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorInfo> handleRateLimitExceededFoundException(RateLimitExceededException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        ErrorInfo errorInfo = new ErrorInfo(requestUrl, ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(errorInfo);
    }

    @RequestMapping("/error")
    public ResponseEntity<ErrorInfo> handleError(final HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = 400;
        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
        }

        ErrorInfo errorInfo = new ErrorInfo(requestUrl, throwable.getMessage());

        return ResponseEntity.status(statusCode).body(errorInfo);
    }
}

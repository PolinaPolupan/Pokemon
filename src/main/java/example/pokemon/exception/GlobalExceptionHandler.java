package example.pokemon.exception;

import example.pokemon.dto.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

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
}


package minhdoswe.socialnetwork.z.exception;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.dto.response.ErrorResponse;
import minhdoswe.socialnetwork.z.exception.auth.AccountDeactivatedException;
import minhdoswe.socialnetwork.z.exception.auth.UserAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Clock clock;

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String code, String message, HttpServletRequest request, Map<String, String> validationErrors) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now(clock))
                .validationErrors(validationErrors)
                .build();
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String code, String message, HttpServletRequest request) {
        return build(status, code, message, request, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest httpServletRequest) {
        return build(HttpStatus.BAD_REQUEST, "DATABASE ERROR", "Database constraint violation", httpServletRequest);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExistsException ex, HttpServletRequest httpServletRequest) {
        return build(HttpStatus.CONFLICT, "USER EXISTS", ex.getMessage(), httpServletRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest httpServletRequest) {
        return build(HttpStatus.UNAUTHORIZED, "AUTH_FAILED", "Invalid identifier or password", httpServletRequest);
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleAccountDeactivated(AccountDeactivatedException ex, HttpServletRequest httpServletRequest) {
        return build(HttpStatus.FORBIDDEN, "ACCOUNT_DEACTIVATED", ex.getMessage(), httpServletRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        // Get ONLY the first validation error
        FieldError firstError = ex.getBindingResult().getFieldErrors().get(0);

        Map<String, String> response = new HashMap<>();
        response.put(firstError.getField(), firstError.getDefaultMessage());

        return ResponseEntity.badRequest().body(response);
    }
}

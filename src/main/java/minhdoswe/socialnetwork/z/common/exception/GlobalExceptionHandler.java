package minhdoswe.socialnetwork.z.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import minhdoswe.socialnetwork.z.common.model.dto.ErrorResponse;
import minhdoswe.socialnetwork.z.modules.auth.internal.exception.AccountDeactivatedException;
import minhdoswe.socialnetwork.z.modules.user.internal.exception.UserAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Clock clock;

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request,
            Map<String, String> validationErrors) {

        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now(clock))
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }

    private ResponseEntity<ErrorResponse> build(
            HttpStatus status,
            String code,
            String message,
            HttpServletRequest request) {

        return build(status, code, message, request, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest httpServletRequest) {

        return build(
                HttpStatus.BAD_REQUEST,
                "DATABASE ERROR",
                "Database constraint violation",
                httpServletRequest);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(
            UserAlreadyExistsException ex,
            HttpServletRequest httpServletRequest) {

        return build(
                HttpStatus.CONFLICT,
                "USER EXISTS",
                ex.getMessage(), httpServletRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest httpServletRequest) {

        return build(
                HttpStatus.UNAUTHORIZED,
                "AUTH_FAILED",
                "Invalid identifier or password",
                httpServletRequest);
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleAccountDeactivated(
            AccountDeactivatedException ex,
            HttpServletRequest httpServletRequest) {

        return build(
                HttpStatus.FORBIDDEN,
                "ACCOUNT_DEACTIVATED",
                ex.getMessage(),
                httpServletRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.computeIfAbsent(error.getField(), str -> new ArrayList<>())
                            .add(error.getDefaultMessage());
                });



        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}

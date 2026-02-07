package project.office.advice;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.office.model.dto.base.response.APIBaseResponseDTO;
import project.office.model.exception.EntityAlreadyExistsException;
import project.office.model.exception.PropertyInvalidException;
import project.office.model.exception.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @ExceptionHandler(exception = EntityAlreadyExistsException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) { //For temporary
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }
    @ExceptionHandler(exception = PropertyInvalidException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handlePropertyInvalidException(PropertyInvalidException e) { //For temporary
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }
    @ExceptionHandler(exception = EntityNotFoundException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleEntityNotFoundException(EntityNotFoundException e){
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleResourceNotFoundException(ResourceNotFoundException e) { //For temporary
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        String errorMessage = "Invalid Format";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                APIBaseResponseDTO.error(errorMessage)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.debug(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                APIBaseResponseDTO.error("Invalid Format")
        );
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleException(Exception e) { //For temporary
        log.error(e.getLocalizedMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }

    @ExceptionHandler(exception = AuthorizationDeniedException.class)
    public ResponseEntity<APIBaseResponseDTO<Object>> handleAuthorizationDeniedException(AuthorizationDeniedException e) { //For temporary
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                APIBaseResponseDTO.error(e.getMessage())
        );
    }
}

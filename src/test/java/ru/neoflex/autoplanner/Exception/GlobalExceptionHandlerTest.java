package ru.neoflex.autoplanner.Exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.exception.GlobalExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidation_ShouldReturnBadRequestWithFieldErrors() {

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "field1", "must not be null");
        FieldError fieldError2 = new FieldError("object", "field2", "must be positive");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("field1=must not be null"));
        assertTrue(response.getBody().getMessage().contains("field2=must be positive"));
    }

    @Test
    void handleConstraint_ShouldReturnBadRequestWithErrorMessage() {
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        when(ex.getMessage()).thenReturn("totalSpent: must be greater than 0");

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleConstraint(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed: totalSpent: must be greater than 0",
                response.getBody().getMessage());
    }

    @Test
    void handleNotFound_WithEntityNotFoundException_ShouldReturn404() {

        EntityNotFoundException ex = new EntityNotFoundException("User not found");

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void handleNotFound_WithNoSuchElementException_ShouldReturn404() {

        NoSuchElementException ex = new NoSuchElementException("Analytics snapshot not found");

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Analytics snapshot not found", response.getBody().getMessage());
    }

    @Test
    void handleConflict_ShouldReturn409() {

        IllegalStateException ex = new IllegalStateException("Snapshot already exists");

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleConflict(ex);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Snapshot already exists", response.getBody().getMessage());
    }

    @Test
    void handleAll_ShouldReturn500ForUnexpectedException() {
        Exception ex = new RuntimeException("Database connection failed");

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleAll(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: Database connection failed",
                response.getBody().getMessage());
    }

    @Test
    void handleAll_ShouldHandleNullMessage() {
        Exception ex = new NullPointerException();

        ResponseEntity<ApiResponseDto<Void>> response = handler.handleAll(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: null", response.getBody().getMessage());
    }
}
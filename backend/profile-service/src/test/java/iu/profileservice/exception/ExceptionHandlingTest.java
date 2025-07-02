package iu.profileservice.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionHandlingTest {

    @Test
    void resourceNotFoundException_ShouldBeRuntimeException() {
        // When
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Resource not found");
    }

    @Test
    void resourceNotFoundException_WithCause_ShouldPreserveCause() {
        // Given
        Throwable cause = new IllegalArgumentException("Invalid argument");

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found", cause);

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Resource not found");
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void resourceNotFoundException_NoArgs_ShouldCreateWithNullMessage() {
        // When
        ResourceNotFoundException exception = new ResourceNotFoundException();

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    void validationException_ShouldBeRuntimeException() {
        // When
        ValidationException exception = new ValidationException("Validation failed");

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Validation failed");
    }

    @Test
    void validationException_WithCause_ShouldPreserveCause() {
        // Given
        Throwable cause = new IllegalStateException("Invalid state");

        // When
        ValidationException exception = new ValidationException("Validation failed", cause);

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Validation failed");
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void validationException_NoArgs_ShouldCreateWithNullMessage() {
        // When
        ValidationException exception = new ValidationException();

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    void exceptionsCanBeThrown() {
        // Test that exceptions can be thrown and caught properly
        assertThatThrownBy(() -> {
            throw new ResourceNotFoundException("Test exception");
        }).isInstanceOf(ResourceNotFoundException.class)
          .hasMessage("Test exception");

        assertThatThrownBy(() -> {
            throw new ValidationException("Test validation");
        }).isInstanceOf(ValidationException.class)
          .hasMessage("Test validation");
    }

    @Test
    void exceptionsHaveDifferentTypes() {
        // When
        ResourceNotFoundException resourceException = new ResourceNotFoundException("Resource error");
        ValidationException validationException = new ValidationException("Validation error");

        // Then
        assertThat(resourceException).isNotInstanceOf(ValidationException.class);
        assertThat(validationException).isNotInstanceOf(ResourceNotFoundException.class);
        
        // Both should be RuntimeExceptions
        assertThat(resourceException).isInstanceOf(RuntimeException.class);
        assertThat(validationException).isInstanceOf(RuntimeException.class);
    }
} 
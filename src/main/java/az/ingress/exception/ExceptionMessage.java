package az.ingress.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
    CLIENT_ERROR("Error from Client"),
    UNEXPECTED_ERROR("Unexpected error occurred");

    private final String message;
}

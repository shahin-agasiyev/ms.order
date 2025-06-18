package az.ingress.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    ORDER_NOT_FOUND("Order with id: %s not found");

    private final String message;
}
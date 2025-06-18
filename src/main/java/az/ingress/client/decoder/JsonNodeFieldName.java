package az.ingress.client.decoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum JsonNodeFieldName {

    CODE("code");

    String value;
}

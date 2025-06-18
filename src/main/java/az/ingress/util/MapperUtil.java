package az.ingress.util;

import java.io.InputStream;

import static az.ingress.mapper.ObjectMapperFactory.OBJECT_MAPPER_FACTORY;

public enum MapperUtil {
    MAPPER_UTIL;

    public <T> T map(InputStream source, Class<T> clazz) {
        try {
            return OBJECT_MAPPER_FACTORY.createObjectMapperInstance().readValue(source, clazz);
        } catch (Exception exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }
}

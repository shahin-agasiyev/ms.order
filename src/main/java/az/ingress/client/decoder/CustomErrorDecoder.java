package az.ingress.client.decoder;

import az.ingress.exception.CustomFeignException;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static az.ingress.client.decoder.JsonNodeFieldName.CODE;
import static az.ingress.exception.ExceptionMessage.CLIENT_ERROR;
import static az.ingress.util.MapperUtil.MAPPER_UTIL;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = CLIENT_ERROR.getMessage();
        var status = response.status();

        JsonNode jsonNode;
        try(var body = response.body().asInputStream()) {
            jsonNode = MAPPER_UTIL.map(body, JsonNode.class);
        } catch (Exception e) {
            throw new CustomFeignException(CLIENT_ERROR.getMessage(), status);
        }

        if (jsonNode.has(CODE.getValue())) errorMessage = jsonNode.get(CODE.getValue()).asText();

        log.error("ActionLog.decode.error Message: {}, Method: {} ", errorMessage, methodKey);
        return new CustomFeignException(errorMessage, status);
    }
}

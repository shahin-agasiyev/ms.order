package az.ingress.client;

import az.ingress.client.decoder.CustomErrorDecoder;
import az.ingress.model.client.ProductResponse;
import az.ingress.model.client.UpdateStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "ms-product",
            url = "${client.urls.ms-product}",
            configuration = CustomErrorDecoder.class)
public interface ProductClient {

    @PutMapping("v1/products/{productId}")
    ProductResponse updateStockQuantity(@RequestBody @Valid UpdateStockRequest stockRequest, @PathVariable Long productId);

    @GetMapping("v1/products/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);
}

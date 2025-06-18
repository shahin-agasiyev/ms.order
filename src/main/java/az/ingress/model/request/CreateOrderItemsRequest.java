package az.ingress.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemsRequest {

    private Integer quantity;
    private Long productId;
}

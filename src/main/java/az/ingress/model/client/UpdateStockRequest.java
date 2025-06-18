package az.ingress.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockRequest {

    private int orderQuantity;
    private Long productId;

    public UpdateStockRequest(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}

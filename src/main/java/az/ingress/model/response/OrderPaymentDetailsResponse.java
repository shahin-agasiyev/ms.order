package az.ingress.model.response;

import az.ingress.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPaymentDetailsResponse {
    private Long orderId;
    private Long userId;
    private Double totalPrice;
    private OrderStatus status;
    private List<OrderItemResponse> items;
}

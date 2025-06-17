package az.ingress.model.criteria;

import az.ingress.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderCriteria {
    private Long userId;
    private OrderStatus status;
}

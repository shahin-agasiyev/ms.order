package az.ingress.service.abstraction;

import az.ingress.model.criteria.OrderCriteria;
import az.ingress.model.criteria.PageCriteria;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderPaymentDetailsResponse;
import az.ingress.model.response.OrderResponse;
import az.ingress.model.response.PageableResponse;

import javax.validation.Valid;

public interface OrderService {
    void createOrder(Long userId, @Valid CreateOrderRequest request);

    OrderResponse getOrderById(Long orderId);

    PageableResponse<OrderResponse> getAllTickets(PageCriteria pageCriteria, OrderCriteria orderCriteria);

    OrderPaymentDetailsResponse getOrderPaymentDetails(Long orderId);
}

package az.ingress.controller;

import az.ingress.model.criteria.OrderCriteria;
import az.ingress.model.criteria.PageCriteria;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderPaymentDetailsResponse;
import az.ingress.model.response.OrderResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static az.ingress.model.constant.HeaderConstants.USER_ID;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createOrder(@RequestHeader(USER_ID) Long userId,
                            @RequestBody @Valid CreateOrderRequest request) {
        orderService.createOrder(userId, request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    public PageableResponse<OrderResponse> getAllTickets(PageCriteria pageCriteria, OrderCriteria orderCriteria) {
        return orderService.getAllTickets(pageCriteria, orderCriteria);
    }

    @GetMapping("/{orderId}/payment-details")
    public OrderPaymentDetailsResponse getOrderPaymentDetails(@PathVariable Long orderId) {
        return orderService.getOrderPaymentDetails(orderId);
    }
}
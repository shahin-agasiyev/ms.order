package az.ingress.mapper;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.entity.OrderItemEntity;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderItemResponse;
import az.ingress.model.response.OrderPaymentDetailsResponse;

import java.util.List;
import java.util.stream.Collectors;

public enum OrderItemMapper {

    ORDER_ITEM_MAPPER;

    public List<OrderItemEntity> getOrderItemEntities(CreateOrderRequest orderRequest, OrderEntity orderEntity) {
        return orderRequest.getOrderItems().stream()
                .map(item -> OrderItemEntity.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .order(orderEntity)
                        .build())
                .collect(Collectors.toList());
    }

    public OrderItemResponse toOrderResponse(OrderItemEntity orderItemEntity) {
        return OrderItemResponse.builder()
                .id(orderItemEntity.getId())
                .productId(orderItemEntity.getProductId())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    public OrderPaymentDetailsResponse toOrderPaymentResponse(Long orderId, OrderEntity orderEntity, List<OrderItemResponse> list) {
        return OrderPaymentDetailsResponse.builder()
                .orderId(orderId)
                .userId(orderEntity.getUserId())
                .items(list)
                .status(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }
}

package az.ingress.mapper;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.criteria.OrderCriteria;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderResponse;
import az.ingress.service.specification.OrderSpecification;

import static az.ingress.model.enums.OrderStatus.PENDING;


public enum OrderMapper {
    ORDER_MAPPER;

    public OrderEntity toOrderEntity(Long userId, CreateOrderRequest orderRequest) {
        return OrderEntity.builder()
                .userId(userId)
                .status(PENDING)
                .build();
    }

    public OrderResponse toOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .status(orderEntity.getStatus())
                .totalPrice(orderEntity.getTotalPrice())
                .userId(orderEntity.getUserId())
                .build();
    }

    public OrderSpecification toSpecification(OrderCriteria orderCriteria) {
        return new OrderSpecification(orderCriteria);
    }
}

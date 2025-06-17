package az.ingress.service.concrete;

import az.ingress.client.ProductClient;
import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.entity.OrderItemEntity;
import az.ingress.dao.repository.OrderItemRepository;
import az.ingress.dao.repository.OrderRepository;
import az.ingress.exception.NotFoundException;
import az.ingress.mapper.OrderMapper;
import az.ingress.model.client.UpdateStockRequest;
import az.ingress.model.criteria.OrderCriteria;
import az.ingress.model.criteria.PageCriteria;
import az.ingress.model.request.CreateOrderItemsRequest;
import az.ingress.model.request.CreateOrderRequest;
import az.ingress.model.response.OrderPaymentDetailsResponse;
import az.ingress.model.response.OrderResponse;
import az.ingress.model.response.PageableResponse;
import az.ingress.service.abstraction.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static az.ingress.exception.ErrorMessage.ORDER_NOT_FOUND;
import static az.ingress.mapper.OrderItemMapper.ORDER_ITEM_MAPPER;
import static az.ingress.mapper.OrderMapper.ORDER_MAPPER;
import static az.ingress.mapper.PageableMapper.PAGEABLE_MAPPER;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceHandler implements OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void createOrder(Long userId, CreateOrderRequest orderRequest) {
        double totalPrice = 0;
        List<UpdateStockRequest> reservedStocks = new ArrayList<>();

        try {
            totalPrice = getTotalPrice(orderRequest, reservedStocks, totalPrice);

            var orderEntity = OrderMapper.ORDER_MAPPER.toOrderEntity(userId, orderRequest);
            orderEntity.setTotalPrice(totalPrice);
            List<OrderItemEntity> itemEntities = ORDER_ITEM_MAPPER.getOrderItemEntities(orderRequest, orderEntity);

            orderEntity.setOrderItems(itemEntities);
            orderRepository.save(orderEntity);

        } catch (Exception e) {
            rollbackReservedStocks(reservedStocks);
            throw new RuntimeException("Order creation failed", e);
        }
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        var orderEntity = findOrderGetByIdOrThrow(orderId);
        return ORDER_MAPPER.toOrderResponse(orderEntity);
    }

    @Override
    public PageableResponse<OrderResponse> getAllTickets(PageCriteria pageCriteria, OrderCriteria orderCriteria) {

        var orders = orderRepository.findAll(
                ORDER_MAPPER.toSpecification(orderCriteria),
                PAGEABLE_MAPPER.toPageRequest(pageCriteria));
        return PAGEABLE_MAPPER.buildPageableResponse(orders, ORDER_MAPPER::toOrderResponse);
    }

    @Override
    public OrderPaymentDetailsResponse getOrderPaymentDetails(Long orderId) {
        var orderEntity = findOrderGetByIdOrThrow(orderId);
        var allItemByOrder = orderItemRepository.findAllByOrder(orderEntity);

        var list = allItemByOrder.stream().map(ORDER_ITEM_MAPPER::toOrderResponse).toList();
        return ORDER_ITEM_MAPPER.toOrderPaymentResponse(orderId, orderEntity, list);
    }


    private double getTotalPrice(CreateOrderRequest orderRequest, List<UpdateStockRequest> reservedStocks, double totalPrice) {
        for (CreateOrderItemsRequest item : orderRequest.getOrderItems()) {
            productClient.updateStockQuantity(new UpdateStockRequest(item.getQuantity()), item.getProductId());
            reservedStocks.add(new UpdateStockRequest(item.getQuantity(), item.getProductId()));

            var product = productClient.getProductById(item.getProductId());
            totalPrice += product.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private OrderEntity findOrderGetByIdOrThrow(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException(ORDER_NOT_FOUND.getMessage(), orderId));
    }

    private void rollbackReservedStocks(List<UpdateStockRequest> reservedStocks) {
        for (UpdateStockRequest reserved : reservedStocks) {
            try {
                productClient.updateStockQuantity(
                        new UpdateStockRequest(-reserved.getOrderQuantity()), reserved.getProductId()
                );
            } catch (Exception e) {
                log.warn("Stock rollback failed for productId: {}", reserved.getProductId(), e);
            }
        }
    }
}

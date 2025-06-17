package az.ingress.dao.repository;

import az.ingress.dao.entity.OrderEntity;
import az.ingress.dao.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllByOrder(OrderEntity order);
}

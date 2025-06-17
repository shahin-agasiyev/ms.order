package az.ingress.dao.repository;

import az.ingress.dao.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

    Page<OrderEntity> findAll(Specification<OrderEntity> specification, @NonNull Pageable pageable);
}

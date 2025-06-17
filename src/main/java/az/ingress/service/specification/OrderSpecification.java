package az.ingress.service.specification;


import az.ingress.dao.entity.OrderEntity;
import az.ingress.model.criteria.OrderCriteria;
import az.ingress.util.PredicateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static az.ingress.dao.entity.OrderEntity.Fields.userId;

@RequiredArgsConstructor
public class OrderSpecification implements Specification<OrderEntity> {

    private final OrderCriteria orderCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<OrderEntity> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .addNullSafety(orderCriteria.getUserId(),
                        it -> cb.equal(root.get(userId), it)
                )
                .addNullSafety(orderCriteria.getStatus(),
                        it -> cb.equal(root.get("status"), it)
                )
                .build();
        return cb.and(predicates);
    }
}

package toy.AllAttributeOfJPA.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import toy.AllAttributeOfJPA.dto.OrderItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;
import toy.AllAttributeOfJPA.dto.QOrderItemDto;
import toy.AllAttributeOfJPA.entity.OrderItem;
import toy.AllAttributeOfJPA.entity.QItem;
import toy.AllAttributeOfJPA.entity.QOrder;
import toy.AllAttributeOfJPA.entity.QOrderItem;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;
import toy.AllAttributeOfJPA.repository.custom.OrderItemCustomRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static toy.AllAttributeOfJPA.entity.QItem.*;
import static toy.AllAttributeOfJPA.entity.QOrder.*;
import static toy.AllAttributeOfJPA.entity.QOrderItem.*;

public class OrderItemRepositoryImpl implements OrderItemCustomRepository {

    private JPAQueryFactory queryFactory;

    @Autowired
    public OrderItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderItemDto> findMyOrderItem(OrderItemSearchCondition condition, Pageable pageable) {
        QueryResults<OrderItemDto> result = queryFactory
                .select(new QOrderItemDto(
                                orderItem.id,
                                item.name,
                                item.price,
                                order.orderDate,
                                order.orderStatus)
                )
                .from(orderItem)
                .join(orderItem.item, item)
                .join(orderItem.order, order)
                .where(
                        NameEq(condition.getName()),
                        StatusEq(condition.getOrderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderItemDto> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression NameEq(String name) {
        return hasText(name) ? item.name.eq(name) : null;
    }

    private BooleanExpression StatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.orderStatus.eq(orderStatus) : null;
    }
}

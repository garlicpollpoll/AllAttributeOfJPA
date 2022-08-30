package toy.AllAttributeOfJPA.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.AllAttributeOfJPA.dto.OrderItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;

public interface OrderItemCustomRepository {

    Page<OrderItemDto> findMyOrderItem(OrderItemSearchCondition condition, Pageable pageable);
}

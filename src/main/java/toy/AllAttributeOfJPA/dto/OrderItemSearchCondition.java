package toy.AllAttributeOfJPA.dto;

import lombok.Data;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

@Data
public class OrderItemSearchCondition {

    private String name;
    private OrderStatus orderStatus;
}

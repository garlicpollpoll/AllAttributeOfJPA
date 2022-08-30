package toy.AllAttributeOfJPA.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.OrderItem;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderItemDto {

    private Long orderId;
    private String itemName;
    private int price;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    public OrderItemDto() {
    }

    @QueryProjection
    public OrderItemDto(Long orderId, String itemName, int price, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.price = price;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        price = orderItem.getOrderPrice();
        orderDate = orderItem.getOrder().getOrderDate();
        orderStatus = orderItem.getOrder().getOrderStatus();
    }
}

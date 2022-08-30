package toy.AllAttributeOfJPA.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderCreateResponse {

    private String itemName;
    private int price;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
}

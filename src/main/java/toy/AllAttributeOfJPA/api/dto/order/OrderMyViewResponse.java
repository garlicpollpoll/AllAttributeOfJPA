package toy.AllAttributeOfJPA.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderMyViewResponse {

    private Long orderItemId;
    private String itemName;
    private int price;
    private LocalDateTime orderItemDate;
    private OrderStatus orderStatus;
}

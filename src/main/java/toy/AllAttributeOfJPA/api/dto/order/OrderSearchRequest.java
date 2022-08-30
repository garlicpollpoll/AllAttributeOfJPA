package toy.AllAttributeOfJPA.api.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

@Data
public class OrderSearchRequest {

    private String name;
    private String orderStatus;

    public OrderSearchRequest(String name, String orderStatus) {
        this.name = name;
        this.orderStatus = orderStatus;
    }

    public OrderSearchRequest() {
    }
}

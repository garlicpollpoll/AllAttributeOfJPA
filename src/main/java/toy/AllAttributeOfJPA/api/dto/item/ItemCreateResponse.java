package toy.AllAttributeOfJPA.api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.entity.Item;

@Data
@AllArgsConstructor
public class ItemCreateResponse {

    private String name;
    private int price;
}

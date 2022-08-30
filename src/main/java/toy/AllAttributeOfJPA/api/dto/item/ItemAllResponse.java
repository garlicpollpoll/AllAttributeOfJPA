package toy.AllAttributeOfJPA.api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.entity.Item;

@Data
@AllArgsConstructor
public class ItemAllResponse {

    private String name;
    private int price;
    private int stockQuantity;
    private String brandName;
    private String category;

    public ItemAllResponse(Item item) {
        name = item.getName();
        price = item.getPrice();
        stockQuantity = item.getStockQuantity();
        brandName = item.getBrandName();
        category = item.getCategory();
    }

    public ItemAllResponse(ItemDto item) {
        name = item.getName();
        price = item.getPrice();
        stockQuantity = item.getStockQuantity();
        brandName = item.getBrandName();
        category = item.getCategory();
    }
}

package toy.AllAttributeOfJPA.api.dto.item;

import lombok.Data;

@Data
public class ItemCreateRequest {

    private String name;
    private int price;
    private int stockQuantity;
    private String brandName;
    private String category;

    public ItemCreateRequest(String name, int price, int stockQuantity, String brandName, String category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandName = brandName;
        this.category = category;
    }

    public ItemCreateRequest() {
    }
}

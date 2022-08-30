package toy.AllAttributeOfJPA.api.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ItemCondition {

    private String brandName;
    private String category;
    private int priceGoe;
    private int priceLoe;

    public ItemCondition(String brandName, String category, int priceGoe, int priceLoe) {
        this.brandName = brandName;
        this.category = category;
        this.priceGoe = priceGoe;
        this.priceLoe = priceLoe;
    }

    public ItemCondition() {
    }
}

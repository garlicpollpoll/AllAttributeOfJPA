package toy.AllAttributeOfJPA.controller.dto;

import lombok.Data;

@Data
public class ItemConditionDto {

    private String priceGoe;
    private String priceLoe;
    private String brandName;
    private String category;

    public ItemConditionDto(String priceGoe, String priceLoe, String brandName, String category) {
        this.priceGoe = priceGoe;
        this.priceLoe = priceLoe;
        this.brandName = brandName;
        this.category = category;
    }

    public ItemConditionDto() {
    }
}

package toy.AllAttributeOfJPA.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ItemForm {

    @NotEmpty
    private String name;
    @NotNull
    private int price;
    @NotNull
    private int stockQuantity;

    @NotEmpty
    private String brandName;
    @NotEmpty
    private String category;

    public ItemForm(String name, int price, int stockQuantity, String brandName, String category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandName = brandName;
        this.category = category;
    }

    public ItemForm() {
    }
}

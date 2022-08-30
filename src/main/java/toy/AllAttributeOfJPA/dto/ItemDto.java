package toy.AllAttributeOfJPA.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private String brandName;
    private String category;

    @QueryProjection
    public ItemDto(Long id, String name, int price, int stockQuantity, String brandName, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandName = brandName;
        this.category = category;
    }
}

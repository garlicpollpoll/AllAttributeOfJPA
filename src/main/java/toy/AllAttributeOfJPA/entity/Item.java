package toy.AllAttributeOfJPA.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String brandName;
    private String category;

    public Item(String name, int price, int stockQuantity, String brandName, String category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandName = brandName;
        this.category = category;
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int remove = getStockQuantity() - quantity;

        if (remove < 0) {
            throw new IllegalStateException("재고가 없습니다.");
        }

        this.stockQuantity = remove;
    }
}

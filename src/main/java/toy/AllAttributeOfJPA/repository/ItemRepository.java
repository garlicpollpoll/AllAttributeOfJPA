package toy.AllAttributeOfJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.AllAttributeOfJPA.entity.Item;
import toy.AllAttributeOfJPA.repository.custom.ItemCustomRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository {
}

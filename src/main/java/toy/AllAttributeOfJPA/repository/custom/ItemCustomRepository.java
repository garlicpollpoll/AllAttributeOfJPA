package toy.AllAttributeOfJPA.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.AllAttributeOfJPA.dto.CategoriesDto;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.entity.Item;

public interface ItemCustomRepository {

    Page<ItemDto> findByCategories(CategoriesDto condition, Pageable pageable);
}

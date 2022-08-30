package toy.AllAttributeOfJPA.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import toy.AllAttributeOfJPA.dto.CategoriesDto;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.entity.Item;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    @Test
    public void test() throws Exception {
        //given
        Item item = new Item("JPA", 20000, 100, null, "책");
        em.persist(item);
        //when
        CategoriesDto condition = new CategoriesDto();
        condition.setCategory("책");
        condition.setPriceGoe(15000);
        condition.setPriceLoe(25000);

        PageRequest page = PageRequest.of(0, 5);

        Page<ItemDto> findItem = itemRepository.findByCategories(condition, page);
        List<ItemDto> result = findItem.getContent();
        //then
        assertThat(result.size()).isEqualTo(1);
    }
}
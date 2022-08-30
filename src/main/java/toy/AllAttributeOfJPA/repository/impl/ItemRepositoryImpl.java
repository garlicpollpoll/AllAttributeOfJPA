package toy.AllAttributeOfJPA.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toy.AllAttributeOfJPA.dto.CategoriesDto;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.dto.QItemDto;
import toy.AllAttributeOfJPA.entity.Item;
import toy.AllAttributeOfJPA.repository.custom.ItemCustomRepository;

import javax.persistence.EntityManager;

import java.util.List;

import static org.springframework.util.StringUtils.*;
import static toy.AllAttributeOfJPA.entity.QItem.*;

public class ItemRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemDto> findByCategories(CategoriesDto condition, Pageable pageable) {
        QueryResults<ItemDto> result = queryFactory
                .select(new QItemDto(
                        item.id,
                        item.name,
                        item.price,
                        item.stockQuantity,
                        item.brandName,
                        item.category
                ))
                .from(item)
                .where(
                        BrandNameEq(condition.getBrandName()),
                        CategoryEq(condition.getCategory()),
                        PriceGoe(condition.getPriceGoe()),
                        PriceLoe(condition.getPriceLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ItemDto> results = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression BrandNameEq(String brandName) {
        return hasText(brandName) ? item.brandName.eq(brandName) : null;
    }

    private BooleanExpression CategoryEq(String category) {
        return hasText(category) ? item.category.eq(category) : null;
    }

    private BooleanExpression PriceGoe(Integer priceGoe) {
        return (priceGoe != null) && (priceGoe != 0) ? item.price.goe(priceGoe) : null;
    }

    private BooleanExpression PriceLoe(Integer priceLoe) {
        return (priceLoe != null) && (priceLoe != 0) ? item.price.loe(priceLoe) : null;
    }


}

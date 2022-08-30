package toy.AllAttributeOfJPA.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.AllAttributeOfJPA.entity.Order;
import toy.AllAttributeOfJPA.entity.OrderItem;
import toy.AllAttributeOfJPA.repository.custom.OrderItemCustomRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemCustomRepository {

    @EntityGraph(attributePaths = {"item", "order"})
    @Query("select oi from OrderItem oi left join oi.order o left join o.member m where m.loginId = :loginId")
    List<OrderItem> findMyOrder(@Param("loginId") String loginId);

    @EntityGraph(attributePaths = {"item"})
    @Override
    Optional<OrderItem> findById(Long aLong);
}

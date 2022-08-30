package toy.AllAttributeOfJPA.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toy.AllAttributeOfJPA.entity.Member;
import toy.AllAttributeOfJPA.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.item i join fetch o.delivery d where o.id = :id")
    Optional<Order> findById(@Param("id") Long orderId);
}

package toy.AllAttributeOfJPA.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import toy.AllAttributeOfJPA.dto.OrderItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;
import toy.AllAttributeOfJPA.entity.*;
import toy.AllAttributeOfJPA.entity.enums.DeliveryStatus;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderItemRepositoryTest {

    @Autowired OrderItemRepository orderItemRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 동적쿼리테스트() throws Exception {
        //given
        createData();
        //when
        OrderItemSearchCondition condition = new OrderItemSearchCondition();
        condition.setName("JPA");
        condition.setOrderStatus(OrderStatus.ORDER);

        PageRequest page = PageRequest.of(0, 5);

        Page<OrderItemDto> result = orderItemRepository.findMyOrderItem(condition, page);
        List<OrderItemDto> findOrderItem = result.getContent();
        //then
        assertThat(findOrderItem.size()).isEqualTo(1);
    }

    @Test
    public void 재고테스트() throws Exception {
        //given
        Member member = new Member("경석", 24, "ks3254", "ks32541007!", new Address("서울", "월드컵로", "1111"));
        Item item = new Item("JPA", 20000, 100, null, "책");
        Delivery delivery = new Delivery(DeliveryStatus.READY, member.getAddress());
        Order order = new Order(member, delivery, LocalDateTime.now(), OrderStatus.ORDER);
        OrderItem orderItem = new OrderItem(item, order, 20000, 2);

        em.persist(member);
        em.persist(item);
        em.persist(delivery);
        em.persist(order);
        em.persist(orderItem);
        //when
        orderItem.createOrderItem(item, orderItem.getCount());
        //then
        assertThat(item.getStockQuantity()).isEqualTo(98);
    }

    @Test
    public void 주문취소테스트() throws Exception {
        //given
        Member member = new Member("경석", 24, "ks3254", "ks32541007!", new Address("서울", "월드컵로", "1111"));
        Item item = new Item("JPA", 20000, 100, null, "책");
        Delivery delivery = new Delivery(DeliveryStatus.READY, new Address("서울", "월드컵로", "1111"));
        Order order = new Order(member, delivery, LocalDateTime.now(), OrderStatus.ORDER);
        OrderItem orderItem = new OrderItem(item, order, 20000, 2);

        em.persist(member);
        em.persist(item);
        em.persist(delivery);
        em.persist(order);
        em.persist(orderItem);
        //when
        orderItem.createOrderItem(item, orderItem.getCount());
        orderItem.cancel();
        //then
        assertThat(item.getStockQuantity()).isEqualTo(100);
    }

    private void createData() {
        Member member = new Member("경석", 24, "ks3254", "ks32541007!", new Address("서울", "월드컵로", "1111"));
        Item item = new Item("JPA", 20000, 100, null, "책");
        Delivery delivery = new Delivery(DeliveryStatus.READY, new Address("서울", "월드컵로", "1111"));
        Order order = new Order(member, delivery, LocalDateTime.now(), OrderStatus.ORDER);
        OrderItem orderItem = new OrderItem(item, order, 20000, 2);

        em.persist(member);
        em.persist(item);
        em.persist(delivery);
        em.persist(order);
        em.persist(orderItem);
    }
}
package toy.AllAttributeOfJPA.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.AllAttributeOfJPA.api.dto.order.OrderSearchRequest;
import toy.AllAttributeOfJPA.api.dto.result.Result;
import toy.AllAttributeOfJPA.dto.OrderItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;
import toy.AllAttributeOfJPA.entity.Member;
import toy.AllAttributeOfJPA.entity.Order;
import toy.AllAttributeOfJPA.entity.OrderItem;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;
import toy.AllAttributeOfJPA.repository.MemberRepository;
import toy.AllAttributeOfJPA.repository.OrderItemRepository;
import toy.AllAttributeOfJPA.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;

    /**
     * OrderItemDto
     */
    @GetMapping("/api/orders/member/{memberId}")
    public Result findMyOrders(@PathVariable("memberId") Long memberId) {
        Member findMember = memberRepository.findById(memberId).get();

        List<OrderItem> myOrder = orderItemRepository.findMyOrder(findMember.getLoginId());

        List<OrderItemDto> collect = myOrder.stream().map(x -> new OrderItemDto(x)).collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * OrderItemSearchCondition
     */
    @GetMapping("/api/orders/search")
    public Result orderSearch(@RequestBody OrderSearchRequest request) {
        OrderItemSearchCondition condition = new OrderItemSearchCondition();
        condition.setName(request.getName());

        OrderStatus orderStatus;

        if (request.getOrderStatus() == null) {
            orderStatus = null;
        }
        else if (request.getOrderStatus().equals("ORDER")) {
            orderStatus = OrderStatus.ORDER;
        }
        else if (request.getOrderStatus().equals("CANCEL")) {
            orderStatus = OrderStatus.CANCEL;
        }
        else {
            orderStatus = null;
        }

        condition.setOrderStatus(orderStatus);

        PageRequest page = PageRequest.of(0, 5);

        Page<OrderItemDto> orderItems = orderItemRepository.findMyOrderItem(condition, page);
        List<OrderItemDto> content = orderItems.getContent();

        return new Result(content);
    }

    @GetMapping("/api/order/cancel/{orderId}")
    @Transactional
    public String orderCancel(@PathVariable("orderId") Long orderId) {
        Order findOrder = orderRepository.findById(orderId).get();


        findOrder.cancel();

        return "주문이 취소되었습니다.";
    }
}

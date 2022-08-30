package toy.AllAttributeOfJPA.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toy.AllAttributeOfJPA.dto.OrderItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;
import toy.AllAttributeOfJPA.entity.Item;
import toy.AllAttributeOfJPA.entity.Order;
import toy.AllAttributeOfJPA.entity.OrderItem;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;
import toy.AllAttributeOfJPA.repository.MemberRepository;
import toy.AllAttributeOfJPA.repository.OrderItemRepository;
import toy.AllAttributeOfJPA.repository.OrderRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public String orderAll(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");
        List<OrderItem> findOrders = orderItemRepository.findMyOrder(loginId);

        model.addAttribute("orders", findOrders);

        return "order/orders";
    }

    @GetMapping("/order/cancel/{orderId}")
    @Transactional
    public String cancel(@PathVariable("orderId") Long orderId) {
        Order findOrder = orderRepository.findById(orderId).get();

        findOrder.cancel();

        return "redirect:/orders";
    }

    @PostMapping("/orders/search")
    public String orderSearch(@RequestParam("itemName") String itemName, @RequestParam("status") String status,
                              Model model) {
        OrderItemSearchCondition condition = new OrderItemSearchCondition();
        condition.setName(itemName);

        OrderStatus orderStatus = OrderStatus.ORDER;

        if (status.equals("ORDER")) {
            orderStatus = OrderStatus.ORDER;
        }
        else if (status.equals("CANCEL")){
            orderStatus = OrderStatus.CANCEL;
        }
        else {
            orderStatus = null;
        }

        condition.setOrderStatus(orderStatus);

        PageRequest page = PageRequest.of(0, 5);

        Page<OrderItemDto> myOrderItem = orderItemRepository.findMyOrderItem(condition, page);
        List<OrderItemDto> content = myOrderItem.getContent();

        model.addAttribute("findOrders", content);

        return "order/orders";
    }
}

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.AllAttributeOfJPA.controller.dto.ItemConditionDto;
import toy.AllAttributeOfJPA.controller.dto.ItemForm;
import toy.AllAttributeOfJPA.dto.CategoriesDto;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.entity.*;
import toy.AllAttributeOfJPA.entity.enums.DeliveryStatus;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;
import toy.AllAttributeOfJPA.repository.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/item/new")
    public String Item() {
        return "item/new";
    }

    @PostMapping("/item/new")
    public String createItem(@Valid ItemForm form) {
        Item item = new Item(form.getName(), form.getPrice(), form.getStockQuantity(), form.getBrandName(), form.getCategory());
        itemRepository.save(item);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String allItems(Model model) {
        List<Item> findItems = itemRepository.findAll();

        model.addAttribute("items", findItems);

        return "item/items";
    }

    @PostMapping("/items")
    public String items(Model model, ItemConditionDto form) {
        CategoriesDto condition = new CategoriesDto();

        if (!form.getPriceGoe().equals("") && !form.getPriceLoe().equals("")) {
            condition.setPriceGoe(Integer.parseInt(form.getPriceGoe()));
            condition.setPriceLoe(Integer.parseInt(form.getPriceLoe()));
        }
        else if (!form.getPriceLoe().equals("")) {
            condition.setPriceLoe(Integer.parseInt(form.getPriceLoe()));
        }
        else if (!form.getPriceGoe().equals("")) {
            condition.setPriceGoe(Integer.parseInt(form.getPriceGoe()));
        }

        condition.setCategory(form.getCategory());
        condition.setBrandName(form.getBrandName());

        PageRequest page = PageRequest.of(0, 20);

        Page<ItemDto> findItems = itemRepository.findByCategories(condition, page);
        List<ItemDto> content = findItems.getContent();

        model.addAttribute("items", content);

        return "item/items";
    }

    @GetMapping("/item/detail/{itemId}")
    public String detailItem(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("id", itemId);

        List<Comment> comments = commentRepository.itemComment(itemId);
        model.addAttribute("comment", comments);

        return "item/detail";
    }

    @GetMapping("/item/buy/{itemId}")
    @Transactional
    public String buyItem(@PathVariable("itemId") Long itemId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");

        Member findMember = memberRepository.findByLoginId(loginId);
        Delivery delivery = new Delivery(DeliveryStatus.READY, findMember.getAddress());
        deliveryRepository.save(delivery);

        Order order = new Order(findMember, delivery, LocalDateTime.now(), OrderStatus.ORDER);
        orderRepository.save(order);

        Item findItem = itemRepository.findById(itemId).get();
        OrderItem orderItem = new OrderItem(findItem, order, findItem.getPrice(), 1);
        orderItemRepository.save(orderItem);

        orderItem.createOrderItem(findItem, 1);

        return "redirect:/";
    }

    @PostMapping("/item/comment/{id}")
    public String createComment(@PathVariable("id") Long itemId, HttpServletRequest request,
                                @RequestParam("comment") String comments, RedirectAttributes redirect) {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");
        Member findMember = memberRepository.findByLoginId(loginId);

        Item findItem = itemRepository.findById(itemId).get();

        Comment comment = new Comment(findMember, findItem, comments);

        commentRepository.save(comment);

        redirect.addAttribute("itemId", itemId);

        return "redirect:/item/detail/{itemId}";
    }
}

package toy.AllAttributeOfJPA.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import toy.AllAttributeOfJPA.api.dto.comment.CommentViewResponse;
import toy.AllAttributeOfJPA.api.dto.comment.CommentWriteRequest;
import toy.AllAttributeOfJPA.api.dto.comment.CommentWriteResponse;
import toy.AllAttributeOfJPA.api.dto.item.ItemAllResponse;
import toy.AllAttributeOfJPA.api.dto.item.ItemCondition;
import toy.AllAttributeOfJPA.api.dto.item.ItemCreateRequest;
import toy.AllAttributeOfJPA.api.dto.item.ItemCreateResponse;
import toy.AllAttributeOfJPA.api.dto.order.OrderCreateResponse;
import toy.AllAttributeOfJPA.api.dto.result.Result;
import toy.AllAttributeOfJPA.dto.CategoriesDto;
import toy.AllAttributeOfJPA.dto.ItemDto;
import toy.AllAttributeOfJPA.dto.OrderItemSearchCondition;
import toy.AllAttributeOfJPA.entity.*;
import toy.AllAttributeOfJPA.entity.enums.DeliveryStatus;
import toy.AllAttributeOfJPA.entity.enums.OrderStatus;
import toy.AllAttributeOfJPA.repository.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * ItemCreateRequest
     * ItemCreateResponse
     */
    @PostMapping("/api/item/new")
    public Result apiCreateItem(@Valid @RequestBody ItemCreateRequest request) {
        Item item = new Item(request.getName(), request.getPrice(), request.getStockQuantity(), request.getBrandName(), request.getCategory());

        itemRepository.save(item);

        ItemCreateResponse itemCreateResponse = new ItemCreateResponse(request.getName(), request.getPrice());

        return new Result(itemCreateResponse);
    }

    /**
     * ItemAllResponse
     */
    @GetMapping("/api/item/all")
    public Result apiAllItem() {
        List<Item> items = itemRepository.findAll();

        List<ItemAllResponse> collect = items.stream().map(x -> new ItemAllResponse(x)).collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * CategoriesDto
     * ItemCondition
     * ItemAllResponse
     */
    @GetMapping("/api/items")
    public Result apiItems(@RequestBody ItemCondition conditions) {
        CategoriesDto condition = new CategoriesDto();
        condition.setBrandName(conditions.getBrandName());
        condition.setCategory(conditions.getCategory());
        condition.setPriceLoe(conditions.getPriceLoe());
        condition.setPriceGoe(conditions.getPriceGoe());

        PageRequest page = PageRequest.of(0, 20);

        Page<ItemDto> findItems = itemRepository.findByCategories(condition, page);
        List<ItemDto> content = findItems.getContent();

        List<ItemAllResponse> collect = content.stream().map(x -> new ItemAllResponse(x)).collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * CommentViewResponse
     */
    @GetMapping("/api/item/detail/{itemId}")
    public Result apiDetailItem(@PathVariable("itemId") Long itemId) {
        List<Comment> comments = commentRepository.itemComment(itemId);

        List<CommentViewResponse> collect = comments.stream().map(x -> new CommentViewResponse(x)).collect(Collectors.toList());

        return new Result(collect);
    }

    /**
     * CommentWriteResponse
     */
    @PostMapping("/api/item/comment/{itemId}/member/{memberId}")
    public Result apiCommentItem(@PathVariable("itemId") Long itemId, @PathVariable("memberId") Long memberId,
                                 @RequestBody CommentWriteRequest request) {
        Member findMember = memberRepository.findById(memberId).get();
        Item findItem = itemRepository.findById(itemId).get();

        Comment comment = new Comment(findMember, findItem, request.getComment());
        commentRepository.save(comment);

        CommentWriteResponse commentWriteResponse = new CommentWriteResponse(
                findMember.getName(), findItem.getName(), request.getComment()
        );

        return new Result(commentWriteResponse);
    }

    /**
     * OrderCreateResponse
     */
    @PostMapping("/api/item/buy/{itemId}/member/{memberId}")
    @Transactional
    public Result apiBuyItem(@PathVariable("itemId") Long itemId, @PathVariable("memberId") Long memberId) {
        Member findMember = memberRepository.findById(memberId).get();

        Delivery delivery = new Delivery(DeliveryStatus.READY, findMember.getAddress());
        deliveryRepository.save(delivery);

        Order order = new Order(findMember, delivery, LocalDateTime.now(), OrderStatus.ORDER);
        orderRepository.save(order);

        Item findItem = itemRepository.findById(itemId).get();

        OrderItem orderItem = new OrderItem(findItem, order, findItem.getPrice(), 1);
        orderItemRepository.save(orderItem);

        orderItem.createOrderItem(findItem, 1);

        OrderCreateResponse orderCreateResponse = new OrderCreateResponse(
                findItem.getName(), findItem.getPrice(), order.getOrderStatus(), order.getOrderDate()
        );

        return new Result(orderCreateResponse);
    }
}

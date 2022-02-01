package com.example.core.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.domain.Order;
import com.example.core.domain.OrderCheckForm;
import com.example.core.domain.OrderForm;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.product.ProductDTO;
import com.example.core.service.member.MemberService;
import com.example.core.service.order.OrderService;
import com.example.core.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService; //하나의 컨트롤러에서 다음과 같이 서비스 여러개를 받아도 괜찮겠지?
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/order")
    public String showOrderList(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        OrderForm orderForm = new OrderForm(null, null, 0);
        model.addAttribute("orderForm", orderForm);
        return "order/order-list";
    }

    @DeleteMapping("order")
    public String deleteOrder(Model model, @RequestParam Long id) {
        orderService.cancel(id);
        return "redirect:/order";
    }

    @GetMapping("/order/order-page")
    public String orderForm(Model model, @ModelAttribute OrderForm orderForm) {
        Long memberId = findMemberId(orderForm);
        Long productId = findProductId(orderForm);
        int quantity = orderForm.getQuantity();
        int calculatePrice = orderService.calculatePrice(memberId, productId, quantity);

        OrderCheckForm orderCheckForm = new OrderCheckForm(memberId, productId, quantity, calculatePrice);
        model.addAttribute("orderCheckForm", orderCheckForm);
        return "order/order-page";
    }

    private Long findProductId(OrderForm orderForm) {
        String productName = orderForm.getProductName();
        Long productId = productService.findOneUsingName(productName);
        return productId;
    }

    private Long findMemberId(OrderForm orderForm) {
        String memberName = orderForm.getMemberName();
        Long memberId = memberService.findOneUsingName(memberName);
        return memberId;
    }

    @PostMapping("/order/order-page")
    public String order(Model model, @ModelAttribute OrderCheckForm orderCheckForm) {
        orderService.purchase(orderCheckForm);
        return "redirect:/order";
    }
}

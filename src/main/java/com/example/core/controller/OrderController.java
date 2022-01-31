package com.example.core.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.domain.Order;
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
        List<MemberDTO> members = memberService.findAll();
        List<ProductDTO> products = productService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("members", members);
        model.addAttribute("products", products);
        return "order/order-list";
    }

    @GetMapping("/order/order-page")
    public String orderForm(Model model, @RequestParam String member, @RequestParam String product,
        @RequestParam Integer quantity) {

        //이름으로 멤버, 상품 찾고, price를 계산.
        int calculatePrice = orderService.calculatePrice(member, product, quantity);

        //폼을 만들어주면 더 효율적이겠다.
        model.addAttribute("calculatePrice", calculatePrice);
        model.addAttribute("productName", product);
        model.addAttribute("quantity", quantity);

        return "order/order-page";
    }
}

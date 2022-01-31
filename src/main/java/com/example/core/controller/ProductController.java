package com.example.core.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.core.domain.member.MemberForm;
import com.example.core.domain.product.ProductDTO;
import com.example.core.domain.product.ProductForm;
import com.example.core.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product")
    public String showProductList(Model model) {
        List<ProductDTO> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @DeleteMapping("/product")
    public String deleteProduct(@RequestParam Long id) {
        productService.dropOut(id);
        return "redirect:/product";
    }

    @GetMapping("/product/register")
    public String registerForm(Model model) {
        ProductForm productForm = new ProductForm(null, 0, 0); //wrapper 객체를 사용합시다...
        model.addAttribute("productForm", productForm);
        return "product/register-form";
    }

    @PostMapping("/product/register")
    public String register(Model model, @ModelAttribute ProductForm productForm) {
        productService.register(productForm);
        return "redirect:/product";
    }

    @GetMapping("/product/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.lookUp(id);
        ProductForm productForm = new ProductForm(null, 0, 0); //wrapper 객체를 사용합시다...
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productForm", productForm);
        return "product/product-info";
    }

    @PostMapping("product/{id}")
    public String modify(@PathVariable Long id, @ModelAttribute ProductForm productForm) {
        productService.modifyDetails(id, productForm);
        return "redirect:/product";
    }

}

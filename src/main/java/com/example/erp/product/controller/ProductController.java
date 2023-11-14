package com.example.erp.product.controller;

import com.example.erp.company.entity.CompanyEntity;
import com.example.erp.product.dto.ProductDto;
import com.example.erp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product_list")
    public String listProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/product_list";
    }

    @GetMapping("/product_add")
    public String productAdds(Model model) {
        List<CompanyEntity> companies = productService.getAllCompanies();
        System.out.println("회사들:" + companies);
        model.addAttribute("companies", companies);
        return "product/product_add";
    }

    @PostMapping("/product_add")
    public ResponseEntity<String> productAdd(@RequestBody ProductDto productDto) {
        try {
            productService.save(productDto);
            return new ResponseEntity<>("데이터가 성공적으로 저장되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("데이터 저장 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product_memo/{id}")
    public String productInfo(Model model, @PathVariable long id) {
        ProductDto productDto = productService.findById(id);
        model.addAttribute("product", productDto);
        return "product/product_memo";
    }

    @GetMapping("/product_edit/{id}")
    public String productEdit(Model model, @PathVariable long id) {
        List<CompanyEntity> companies = productService.getAllCompanies();
        ProductDto productDto = productService.findById(id);
        model.addAttribute("product", productDto);
        model.addAttribute("companies", companies);

        return "product/product_edit";
    }

    @PostMapping("/product_edit_ok")
    public String productEditOk(@RequestParam(name = "id") Long id, @ModelAttribute ProductDto productDto) {
        productService.update(id, productDto);
        return "redirect:/product_list";
    }
}

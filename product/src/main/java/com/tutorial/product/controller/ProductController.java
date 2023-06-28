package com.tutorial.product.controller;

import com.tutorial.product.dto.ProductRequestDTO;
import com.tutorial.product.dto.ProductResponseDTO;
import com.tutorial.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
    productService.createProduct(productRequestDTO);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }


    @PutMapping("/{Id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateProduct(@PathVariable String Id, @RequestBody ProductRequestDTO productRequestDTO) {
        productService.updateProduct(Id, productRequestDTO);
    }

}


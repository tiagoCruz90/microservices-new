package com.tutorial.product.controller;

import com.tutorial.product.dto.ProductRequestDTO;
import com.tutorial.product.dto.ProductResponseDTO;
import com.tutorial.product.service.ProductService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Timed
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


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateProduct(@PathVariable String id, @RequestBody ProductRequestDTO productRequestDTO) {
        productService.updateProduct(id, productRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

}


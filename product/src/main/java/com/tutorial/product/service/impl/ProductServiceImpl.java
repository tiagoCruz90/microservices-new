package com.tutorial.product.service.impl;

import com.tutorial.product.domain.Product;
import com.tutorial.product.dto.ProductRequestDTO;
import com.tutorial.product.dto.ProductResponseDTO;
import com.tutorial.product.repository.ProductRepository;
import com.tutorial.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public void createProduct(ProductRequestDTO productRequestDTO) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} created successfully", product.getId());
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products =  productRepository.findAll();

        return products.stream().map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()).toList();
    }

    @Override
    public void updateProduct(String Id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(Id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());

        productRepository.save(product);
        log.info("Product {} updated successfully", product.getId());
    }


}

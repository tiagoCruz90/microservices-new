package com.tutorial.product.service;

import com.tutorial.product.dto.ProductRequestDTO;
import com.tutorial.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequestDTO productRequestDTO);

    List<ProductResponseDTO> getAllProducts();

    void updateProduct(String id, ProductRequestDTO productRequestDTO);

    void deleteProduct(String id);

    void deleteAllProducts();
}

package com.tutorial.product;

import com.tutorial.product.dto.ProductRequestDTO;
import com.tutorial.product.dto.ProductResponseDTO;
import com.tutorial.product.repository.ProductRepository;
import com.tutorial.product.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;


    @AfterEach
    void tearDown() {
        productRepository.deleteAll();}

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }


    private ProductRequestDTO getProductRequest(){
        return ProductRequestDTO.builder()
                .name("Iphone 12")
                .description("Apple Iphone 12")
                .price(BigDecimal.valueOf(1200))
                .build();
    }


    @Test
    void createProductIT() throws Exception {

        ProductRequestDTO productRequestDTO = getProductRequest();
        String productRequestDTOAsString = objectMapper.writeValueAsString(productRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(productRequestDTOAsString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }


    @Test
    void getAllProductsIT() throws Exception {

        ProductRequestDTO productRequestDTO = getProductRequest();
        productService.createProduct(productRequestDTO);

        List<ProductResponseDTO> productResponseDTOList = productService.getAllProducts();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productResponseDTOList.toString()))
                .andExpect(status().isOk());
        Assertions.assertEquals(1, productRepository.findAll().size());
        }


    @Test
    void updateProductIT(){
        ProductRequestDTO productRequestDTO = getProductRequest();
        productService.createProduct(productRequestDTO);

        List<ProductResponseDTO> productResponseDTOList = productService.getAllProducts();
        ProductResponseDTO productResponseDTO = productResponseDTOList.get(0);
        String id = productResponseDTO.getId();

        ProductRequestDTO productRequestDTO1 = ProductRequestDTO.builder()
                .name("Iphone 13")
                .description("Apple Iphone 13")
                .price(BigDecimal.valueOf(1300))
                .build();
        productService.updateProduct(id, productRequestDTO1);
        Assertions.assertEquals(1, productRepository.findAll().size());
        Assertions.assertEquals("Iphone 13", productRepository.findAll().get(0).getName());
        Assertions.assertEquals("Apple Iphone 13", productRepository.findAll().get(0).getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(1300), productRepository.findAll().get(0).getPrice());
    }
    }


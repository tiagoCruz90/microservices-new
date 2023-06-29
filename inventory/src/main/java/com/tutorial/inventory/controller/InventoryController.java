package com.tutorial.inventory.controller;

import com.tutorial.inventory.dto.InventoryResponseDTO;
import com.tutorial.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
        public List<InventoryResponseDTO> isInStock(@RequestParam List<String> skuCode) {
             return inventoryService.isInStock(skuCode);
    }
}

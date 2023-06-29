package com.example.inventory.service;

import com.example.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDTO> isInStock(List<String> skuCode);
}

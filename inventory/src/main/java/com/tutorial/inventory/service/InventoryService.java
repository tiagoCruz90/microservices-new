package com.tutorial.inventory.service;

import com.tutorial.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryResponseDTO> isInStock(List<String> skuCode);
}

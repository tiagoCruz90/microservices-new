package com.example.inventory.repository;

import com.example.inventory.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
        List<Inventory> findBySkuCodeIn(List<String> skuCode);
}

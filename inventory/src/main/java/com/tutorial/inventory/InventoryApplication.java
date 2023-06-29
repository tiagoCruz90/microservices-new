package com.tutorial.inventory;

import com.tutorial.inventory.domain.Inventory;
import com.tutorial.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
        @Bean
    public CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone_13");
            inventory.setQuantity(100);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iphone_13_pro");
            inventory2.setQuantity(0);

            repository.save(inventory);
            repository.save(inventory2);
        };
    }
}

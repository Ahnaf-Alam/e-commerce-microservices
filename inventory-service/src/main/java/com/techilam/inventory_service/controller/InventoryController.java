package com.techilam.inventory_service.controller;

import com.techilam.inventory_service.dto.InventoryRequest;
import com.techilam.inventory_service.dto.InventoryResponse;
import com.techilam.inventory_service.model.Inventory;
import com.techilam.inventory_service.service.InventoryService;
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
    public List<InventoryResponse> isInStock(@RequestParam("skuCodes") List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrUpdateInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createOrUpdateInventory(inventoryRequest);
        return "Inventory created successfully";
    }
}

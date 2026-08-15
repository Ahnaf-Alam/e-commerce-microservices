package com.techilam.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryRequest {
    private String skuCode;
    private int  quantity;
}

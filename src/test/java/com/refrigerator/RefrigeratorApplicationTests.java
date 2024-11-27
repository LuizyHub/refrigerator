package com.refrigerator;

import com.refrigerator.inventory.dto.InventoryCreateDto;
import com.refrigerator.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RefrigeratorApplicationTests {

	@Autowired
	private InventoryService inventoryService;

	@Test
	void contextLoads() {
		InventoryCreateDto createDto = new InventoryCreateDto();
		createDto.setRefrigId(1L);
		createDto.setItemId(1L);
		createDto.setUnitId(1L);
		createDto.setAmount(200.0);


		inventoryService.addInventory(1L, createDto);
	}

}

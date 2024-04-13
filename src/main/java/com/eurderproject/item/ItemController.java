package com.eurderproject.item;


import com.eurderproject.customer.CustomerController;
import com.eurderproject.customer.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDto> getAllItems() {
        logger.info("Received request to get all items.");
        List<ItemDto> items = itemService.getAllItems();
        logger.info("Returning " + items.size() + " items");
        return items;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestBody CreateItemDto createItemDto) {
        logger.info("Received request to add a new item.");
        ItemDto createdItemDto = itemService.createItem(createItemDto);
        logger.info("Added item: " + createdItemDto);
        return createdItemDto;
    }

    @PutMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable UUID itemId, @RequestBody UpdateItemDto updateItemDto) {
        logger.info("Received request to edit an item.");
        ItemDto updatedItem = itemService.updateItem(itemId, updateItemDto);
        logger.info("Updated item: " + updatedItem + " with id: " + itemId);
        return updatedItem;
    }
}
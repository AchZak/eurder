package com.eurderproject.item;


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
    public ItemDto createItem(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateItemDto createItemDto) {
        logger.info("Received request to create a new item.");
        ItemDto createdItemDto = itemService.createItem(authorizationHeader, createItemDto);
        logger.info("Created item: " + createdItemDto);
        return createdItemDto;
    }

    @PutMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable UUID itemId, @RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateItemDto updateItemDto) {
        logger.info("Received request to edit an item.");
        ItemDto updatedItem = itemService.updateItem(authorizationHeader,itemId, updateItemDto);
        logger.info("Updated item: " + updatedItem + " with id: " + itemId);
        return updatedItem;
    }
}
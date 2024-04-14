package com.eurderproject.item;

import com.eurderproject.customer.CustomerService;
import com.eurderproject.exception.ItemNotFoundException;
import com.eurderproject.security.Permission;
import com.eurderproject.security.SecurityService;
import com.eurderproject.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDtoMapper itemDtoMapper;
    private final SecurityService securityService;

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public ItemService(ItemRepository itemRepository, ItemDtoMapper itemDtoMapper, SecurityService securityService) {
        this.itemRepository = itemRepository;
        this.itemDtoMapper = itemDtoMapper;
        this.securityService = securityService;
    }

    public List<ItemDto> getAllItems() {
        return itemDtoMapper.mapToDtoList(itemRepository.getAllItems());
    }

    public ItemDto getItemById(UUID itemId) {
        Item item = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + itemId));
        return itemDtoMapper.mapToDto(item);
    }

    public ItemDto updateItem(String authorizationHeader, UUID itemId, UpdateItemDto updatedItemDto) {
        User authenticatedUser = securityService.authenticate(authorizationHeader);
        securityService.authorize(authenticatedUser, Permission.UPDATE_ITEM);
        Item existingItem = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + itemId));

        existingItem.setName(updatedItemDto.name());
        existingItem.setDescription(updatedItemDto.description());
        existingItem.setPrice(updatedItemDto.price());
        existingItem.setAmountInStock(updatedItemDto.amountInStock());

        itemRepository.save(existingItem);
        return itemDtoMapper.mapToDto(existingItem);
    }

    public ItemDto createItem(String authorizationHeader, CreateItemDto createItemDto) {
        User authenticatedUser = securityService.authenticate(authorizationHeader);
        securityService.authorize(authenticatedUser, Permission.CREATE_ITEM);
        Item item = itemDtoMapper.mapFromDto(createItemDto);
        itemRepository.save(item);
        logger.info("Item created with ID: {}", item.getItemId());
        return itemDtoMapper.mapToDto(item);
    }
}
package com.eurderproject.item;

import com.eurderproject.customer.Customer;
import com.eurderproject.customer.CustomerDto;
import com.eurderproject.exception.CustomerNotFoundException;
import com.eurderproject.exception.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDtoMapper itemDtoMapper;

    public ItemService(ItemRepository itemRepository, ItemDtoMapper itemDtoMapper) {
        this.itemRepository = itemRepository;
        this.itemDtoMapper = itemDtoMapper;
    }

    public List<ItemDto> getAllItems() {
        return itemDtoMapper.mapToDtoList(itemRepository.getAllItems());
    }

    public ItemDto getItemById(UUID itemId) {
        Item item = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + itemId));
        return itemDtoMapper.mapToDto(item);
    }

    public ItemDto updateItem(UUID itemId, UpdateItemDto updatedItemDto) {
        Item existingItem = itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with ID: " + itemId));

        existingItem.setName(updatedItemDto.name());
        existingItem.setDescription(updatedItemDto.description());
        existingItem.setPrice(updatedItemDto.price());
        existingItem.setAmountInStock(updatedItemDto.amountInStock());

        itemRepository.save(existingItem);
        return itemDtoMapper.mapToDto(existingItem);
    }

    public ItemDto addItem(CreateItemDto createItemDto) {
        Item item = itemDtoMapper.mapFromDto(createItemDto);
        itemRepository.save(item);
        return itemDtoMapper.mapToDto(item);
    }
}

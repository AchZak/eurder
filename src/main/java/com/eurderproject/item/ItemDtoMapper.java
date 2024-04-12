package com.eurderproject.item;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDtoMapper {

    public ItemDto mapToDto(Item item) {
        return new ItemDto(item.getItemId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInStock());
    }

    public List<ItemDto> mapToDtoList(Collection<Item> itemList) {
        return itemList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Item mapFromDto(CreateItemDto createItemDto) {
        return new Item(
                createItemDto.name(),
                createItemDto.description(),
                createItemDto.price(),
                createItemDto.amountInStock()
        );
    }
}

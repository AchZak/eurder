package com.eurderproject.item;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ItemRepository {

    private Map<UUID, Item> items;

    public ItemRepository(Map<UUID, Item> items) {
        this.items = items;
    }

    public void save(Item item){
        items.put(item.getItemId(), item);
    }

    public Collection<Item> getAllItems() {
        return items.values();
    }

    public Optional<Item> findItemById(UUID itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

}

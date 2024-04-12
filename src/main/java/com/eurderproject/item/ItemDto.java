package com.eurderproject.item;

import java.util.UUID;

public record ItemDto(UUID itemId, String name, String description, double price, int amountInStock) {
}

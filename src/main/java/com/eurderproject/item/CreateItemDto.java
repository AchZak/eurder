package com.eurderproject.item;

public record CreateItemDto(String name, String description, double price, int amountInStock) {
}

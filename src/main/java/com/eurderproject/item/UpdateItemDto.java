package com.eurderproject.item;

public record UpdateItemDto(String name, String description, double price, int amountInStock) {
}

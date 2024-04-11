package com.eurderproject.item;

import java.util.UUID;

public class Item {

    private UUID itemId;
    private String name;
    private String description;
    private double price;
    private int amountInStock;

    public Item(String name, String description, double price, int amountInStock) {
        this.itemId = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInStock = amountInStock;
    }

    public UUID getItemId(){
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAmountInStock() {
        return amountInStock;
    }
}

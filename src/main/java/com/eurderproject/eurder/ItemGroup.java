package com.eurderproject.eurder;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    private UUID itemId;
    private int amount;
    private LocalDate shippingDate;

    public ItemGroup(UUID itemId, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }

}

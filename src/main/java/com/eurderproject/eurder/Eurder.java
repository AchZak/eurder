package com.eurderproject.eurder;

import java.util.Collection;
import java.util.UUID;

public class Eurder {

    private UUID eurderId;
    private UUID customerId;
    private Collection<ItemGroup> itemGroups;

    //private LocalDate orderDate;

    public Eurder(UUID customerId, Collection<ItemGroup> itemGroups) {
        eurderId = UUID.randomUUID();
        this.customerId = customerId;
        this.itemGroups = itemGroups;
    }

    public UUID getEurderId() {
        return eurderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Collection<ItemGroup> getItemGroups() {
        return itemGroups;
    }
}



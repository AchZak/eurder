package com.eurderproject.eurder;

import java.util.Collection;
import java.util.UUID;

public record EurderDto(UUID eurderId, UUID customerId, Collection<ItemGroup> itemGroups) {
}

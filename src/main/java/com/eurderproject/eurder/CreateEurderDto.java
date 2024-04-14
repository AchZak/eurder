package com.eurderproject.eurder;

import java.util.Collection;

public record CreateEurderDto(Collection<ItemGroup>itemGroups, double totalPrice) {

}

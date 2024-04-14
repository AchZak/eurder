package com.eurderproject.eurder;


import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EurderDtoMapper {

    public EurderDto mapToDto(Eurder eurder) {
        return new EurderDto(eurder.getEurderId(), eurder.getCustomerId(), eurder.getItemGroups());
    }


    public List<EurderDto> mapToDtoList(Collection<Eurder> itemList) {
        return itemList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}

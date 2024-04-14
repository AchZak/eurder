package com.eurderproject.eurder;

import com.eurderproject.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class EurderRepository {

    private Map<UUID, Eurder> eurders;

    public EurderRepository(Map<UUID, Eurder> eurders) {
        this.eurders = eurders;
    }

    public void save(Eurder eurder){
        eurders.put(eurder.getEurderId(), eurder);
    }

    public Collection<Eurder> getAllEurdersFromCustomerId(UUID customerId){
        return eurders.values().stream()
                .filter(eurder -> eurder.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
}

package com.eurderproject.eurder;

import com.eurderproject.exception.ItemNonExistingException;
import com.eurderproject.item.Item;
import com.eurderproject.item.ItemRepository;
import com.eurderproject.security.Permission;
import com.eurderproject.security.SecurityService;
import com.eurderproject.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class EurderService {
    private final Logger logger = LoggerFactory.getLogger(EurderService.class);
    private final EurderRepository eurderRepository;
    private final EurderDtoMapper eurderDtoMapper;
    private final SecurityService securityService;
    private final ItemRepository itemRepository;

    public EurderService(EurderRepository eurderRepository, EurderDtoMapper eurderDtoMapper, SecurityService securityService, ItemRepository itemRepository) {
        this.eurderRepository = eurderRepository;
        this.eurderDtoMapper = eurderDtoMapper;
        this.securityService = securityService;
        this.itemRepository = itemRepository;
    }

    public CreateEurderDto createEurder(String authorizationHeader, CreateEurderDto createEurderDto) {
        User authenticatedUser = securityService.authenticate(authorizationHeader);
        securityService.authorize(authenticatedUser, Permission.CREATE_ORDER);
        validateItemIds(createEurderDto.itemGroups());
        Eurder eurder = new Eurder(authenticatedUser.getUserId(),createEurderDto.itemGroups());
        calculateShippingDates(eurder);
        eurderRepository.save(eurder);
        return new CreateEurderDto(eurder.getItemGroups(), calculateEurderPrice(eurder));
    }

    private void validateItemIds(Collection<ItemGroup> itemGroups) {
        for (ItemGroup itemGroup : itemGroups) {
            UUID itemId = itemGroup.getItemId();
            if (itemRepository.findItemById(itemId).isEmpty()) {
                logger.info("Item with ID: {} does not exist.", itemId);
                throw new ItemNonExistingException("Item with ID: " + itemId + " does not exist.");
            }
        }
    }

    private void calculateShippingDates(Eurder eurder) {
        LocalDate orderDate = LocalDate.now();
        for (ItemGroup itemGroup : eurder.getItemGroups()) {
            int orderedAmount = itemGroup.getAmount();
            UUID itemId = itemGroup.getItemId();

            Optional<Item> optionalItem = findItemByIdOrThrow(itemId);
            int availableStock = optionalItem.get().getAmountInStock();
            LocalDate shippingDate = orderDate.plusDays(orderedAmount > availableStock ? 7 : 1);
            itemGroup.setShippingDate(shippingDate);

            updateStock(itemGroup, availableStock);
        }
    }

    private void updateStock(ItemGroup itemGroup, int availableStock) {
        Optional<Item> optionalItem = findItemByIdOrThrow(itemGroup.getItemId());
        Item item = optionalItem.get();

        if (itemGroup.getAmount() > availableStock) {
            item.setAmountInStock(0);
        } else {
            item.setAmountInStock(availableStock - itemGroup.getAmount());
        }
        itemRepository.save(item);
    }

    private Optional<Item> findItemByIdOrThrow(UUID itemId) {
        return Optional.ofNullable(itemRepository.findItemById(itemId)
                .orElseThrow(() -> new ItemNonExistingException("Item doesn't exist with ID: " + itemId)));
    }

    private double calculateItemGroupPrice(ItemGroup itemGroup) {
        Optional<Item> optionalItem = itemRepository.findItemById(itemGroup.getItemId());
        return optionalItem.map(item -> item.getPrice() * itemGroup.getAmount())
                .orElseThrow(() -> new ItemNonExistingException("Item doesn't exist"));
    }

    public double calculateEurderPrice(Eurder eurder){
        return eurder.getItemGroups().stream()
                .mapToDouble(this::calculateItemGroupPrice)
                .sum();
    }
}

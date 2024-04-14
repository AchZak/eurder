package com.eurderproject.eurder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eurders")
public class EurderController {

    private final EurderService eurderService;
    private final Logger logger = LoggerFactory.getLogger(EurderController.class);

    public EurderController(EurderService eurderService) {
        this.eurderService = eurderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEurderDto createEurder(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateEurderDto createEurderDto) {
        logger.info("Received request to add a new eurder to current customer.");
        CreateEurderDto createdEurderDto = eurderService.createEurder(authorizationHeader, createEurderDto);
        logger.info("Added eurder: " + createdEurderDto);
        return createdEurderDto;
    }
}

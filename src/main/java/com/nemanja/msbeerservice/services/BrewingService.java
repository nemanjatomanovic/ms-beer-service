package com.nemanja.msbeerservice.services;

import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.events.BrewBeerEvent;
import com.nemanja.msbeerservice.repositories.BeerRepository;
import com.nemanja.msbeerservice.services.inventory.BeerInventoryService;
import com.nemanja.msbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    @Value("${brewing.request.queue}")
    private String queueName;


    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer qoh = beerInventoryService.getOnhandInventiry(beer.getId());

            log.debug("Min Onhand is {} ", beer.getMinOnHand());
            log.debug("Inventory is {} ", qoh);

            if (beer.getMinOnHand() >= qoh) {
                jmsTemplate.convertAndSend(queueName, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}

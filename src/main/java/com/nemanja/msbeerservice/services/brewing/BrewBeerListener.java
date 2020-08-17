package com.nemanja.msbeerservice.services.brewing;

import com.nemanja.msbeerservice.config.JmsConfig;
import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.events.BrewBeerEvent;
import com.nemanja.msbeerservice.events.NewInventoryEvent;
import com.nemanja.msbeerservice.repositories.BeerRepository;
import com.nemanja.msbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    /**
     * Ovde osluskujemo dogadjaje koji se desavaju u metodi {@link com.nemanja.msbeerservice.services.brewing.BrewingService#checkForLowInventory()}
     * U toj metodi saljemo poruke na kanal definisan u konstanti
     */
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}

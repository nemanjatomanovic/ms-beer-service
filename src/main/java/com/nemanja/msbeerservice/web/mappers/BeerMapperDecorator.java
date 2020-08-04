package com.nemanja.msbeerservice.web.mappers;

import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.services.inventory.BeerInventoryService;
import com.nemanja.msbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper beerMapper;

    /**
     * MapStruct zahtjeva no-arghs konstruktor te s tim u vezi radimo
     * method level autowire
     */
    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto dto = beerMapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventiry(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }
}

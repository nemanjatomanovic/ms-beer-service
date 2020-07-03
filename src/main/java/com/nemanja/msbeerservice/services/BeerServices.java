package com.nemanja.msbeerservice.services;

import com.nemanja.msbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerServices {
    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}

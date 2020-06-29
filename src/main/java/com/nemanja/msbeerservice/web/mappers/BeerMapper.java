package com.nemanja.msbeerservice.web.mappers;

import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);

    Beer BeerDtoToBeer(BeerDto beerDto);
}
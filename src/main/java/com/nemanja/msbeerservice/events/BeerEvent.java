package com.nemanja.msbeerservice.events;

import com.nemanja.msbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 8923523615697808803L;

    private final BeerDto beerDto;


}

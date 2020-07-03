package com.nemanja.msbeerservice.web.contoller;

import com.nemanja.msbeerservice.services.BeerServices;
import com.nemanja.msbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

    private final BeerServices beerServices;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById (@PathVariable("beerId")UUID beerId){
        return new ResponseEntity<>(beerServices.getById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity<>(beerServices.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity<>(beerServices.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}

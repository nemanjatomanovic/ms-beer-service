package com.nemanja.msbeerservice.web.contoller;

import com.nemanja.msbeerservice.services.BeerServices;
import com.nemanja.msbeerservice.web.model.BeerDto;
import com.nemanja.msbeerservice.web.model.BeerPageList;
import com.nemanja.msbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerServices beerServices;

    @GetMapping(produces = {"application/json"},path = "beer")
    public ResponseEntity<BeerPageList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "beerName", required = false) String beerName,
                                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                  @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPageList beerPageList = beerServices.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerPageList, HttpStatus.OK);
    }


    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId, @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(beerServices.getById(beerId,showInventoryOnHand), HttpStatus.OK);
    }

    @PostMapping(path = "beer")
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerServices.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerServices.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUPC(@PathVariable("upc")String upc){

        return new ResponseEntity<>(beerServices.getByUPC(upc), HttpStatus.OK);
    }
}

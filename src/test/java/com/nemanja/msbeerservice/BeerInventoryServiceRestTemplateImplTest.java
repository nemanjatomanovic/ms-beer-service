package com.nemanja.msbeerservice;

import com.nemanja.msbeerservice.bootstrap.BeerLoader;
import com.nemanja.msbeerservice.services.inventory.BeerInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {


    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getOnhandInventory() {

//          Integer qoh = beerInventoryService.getOnhandInventiry(BeerLoader.BEER_1_UUID);

  //      System.out.println(qoh);

    }
}
package com.nemanja.msbeerservice.bootstrap;

import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                    .beerName("The Miss Quincy")
                    .beerStyle("ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(3210938210321L)
                    .price(new BigDecimal("2.45"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("ALE")
                    .quantityToBrew(180)
                    .minOnHand(15)
                    .upc(120983213812L)
                    .price(new BigDecimal("3.15"))
                    .build());
        }

    }
}

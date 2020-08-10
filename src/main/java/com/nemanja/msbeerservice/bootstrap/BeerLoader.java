package com.nemanja.msbeerservice.bootstrap;

import com.nemanja.msbeerservice.domain.Beer;
import com.nemanja.msbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {


    public static final String BEER_1_UPC = "063214569812";
    public static final String BEER_2_UPC = "063987103524";
    public static final String BEER_3_UPC = "008369421087";

    private final BeerRepository beerRepository;

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
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("2.45"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(180)
                    .minOnHand(15)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("3.15"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Lav")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(180)
                    .minOnHand(15)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("2.45"))
                    .build());
        }


    }

    }


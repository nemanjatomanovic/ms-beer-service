package com.nemanja.msbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnhandInventiry(UUID beerId);
}

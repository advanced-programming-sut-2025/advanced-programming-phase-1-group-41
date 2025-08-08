package com.CEliconValley.client.view.screen.maps;

import com.CEliconValley.common.VillageData;
import com.CEliconValley.models.locations.Village;
import com.CEliconValley.models.locations.Location;

public class VillageMap implements Location {
    public VillageData villageData;

    public VillageMap(VillageData villageData) {
        this.villageData = villageData;
    }
}

package com.CEliconValley.client.view.screen.maps;

import com.CEliconValley.common.FarmData;
import com.CEliconValley.models.locations.Location;

public class FarmMap implements Location {
    public FarmData farmData;

    public FarmMap(FarmData farmData) {
        this.farmData = farmData;
    }
}

package com.CEliconValley.common;

import com.CEliconValley.models.buildings.Building;
import dev.morphia.annotations.Embedded;


@Embedded
public class BuildingData {
    String name;
    int x;
    int y;
    int anchorX;
    int anchorY;

    public BuildingData() {
    }

    public BuildingData(Building building) {
        this.name = building.getName();
        this.x = building.getX();
        this.y = building.getY();
        this.anchorX = building.getAnchorX();
        this.anchorY = building.getAnchorY();
    }
}

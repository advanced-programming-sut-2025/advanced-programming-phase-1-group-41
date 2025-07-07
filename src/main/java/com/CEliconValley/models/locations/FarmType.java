package com.CEliconValley.models.locations;

public enum FarmType {
    Mountain(3, 1, 1),
    Swamp(1, 2, 2),
    Jungle(1, 1, 3),

    // TODO
    ;
    final int rockCoefficient;
    final int LakeCoefficient;
    final int treeCoefficient;

    FarmType(int rockCoefficient, int LakeCoefficient, int treeCoefficient) {
        this.rockCoefficient = rockCoefficient;
        this.LakeCoefficient = LakeCoefficient;
        this.treeCoefficient = treeCoefficient;
    }

    public int getRockCoefficient() {
        return rockCoefficient;
    }
    public int getLakeCoefficient() {
        return LakeCoefficient;
    }
    public int getTreeCoefficient() {
        return treeCoefficient;
    }



}

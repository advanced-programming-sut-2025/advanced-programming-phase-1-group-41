package org.example.models.tools;

public enum FishingRodLevel {
    Training(8,0.1),
    Bamboo(8,0.5),
    FiberGlass(6,0.9),
    Iridium(4,1.2);

    private int energyUsage;
    private double pole;
    FishingRodLevel(int energyUsage, double pole) {
        this.energyUsage = energyUsage;
        this.pole = pole;
    }
    public int getEnergyUsage(){
        return energyUsage;
    }
    public double getPole(){
        return pole;
    }

}

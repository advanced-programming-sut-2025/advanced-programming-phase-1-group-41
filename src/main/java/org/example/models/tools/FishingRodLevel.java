package org.example.models.tools;

public enum FishingRodLevel {
    Training(8,0.1, "TrainingRod"),
    Bamboo(8,0.5, "BambooRod"),
    FiberGlass(6,0.9, "FiberglassRod"),
    Iridium(4,1.2, "IridiumRod"),;

    private int energyUsage;
    private double pole;
    private String name;
    FishingRodLevel(int energyUsage, double pole, String name) {
        this.energyUsage = energyUsage;
        this.pole = pole;
        this.name = name;
    }
    public int getEnergyUsage(){
        return energyUsage;
    }
    public double getPole(){
        return pole;
    }

    public String getName() {
        return name;
    }

    public static FishingRodLevel parseFishingRodLevel(String input) {
        for (FishingRodLevel value : FishingRodLevel.values()) {
            if(value.getName().equalsIgnoreCase(input)) {
                return value;
            }
        }
        return null;
    }

    public static FishingRod parseFishingRod(String string) {
        for (FishingRodLevel value : FishingRodLevel.values()) {
            if(value.getName().equalsIgnoreCase(string)) {
                return new FishingRod(value);
            }
        }
        return null;
    }
}

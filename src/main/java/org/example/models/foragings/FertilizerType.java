package org.example.models.foragings;

public enum FertilizerType {
    BasicRetainingSoil("Basic Retaining Soil", 100),
    QualityRetainingSoil("Quality Retaining Soil", 150),
    DeluxeRetainingSoil("Deluxe Retaining Soil", 150),
    GrassStarter("Grass Starter", 100),
    PlantGrow("Plant Grow", 100),

    ;
    private final String name;
    private final int price;

    FertilizerType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static FertilizerType parseFertilizerType(String fertilizerType) {
        for (FertilizerType value : FertilizerType.values()) {
            if(value.getName().equalsIgnoreCase(fertilizerType)){
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
}

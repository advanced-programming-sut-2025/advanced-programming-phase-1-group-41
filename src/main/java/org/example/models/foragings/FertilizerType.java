package org.example.models.foragings;

public enum FertilizerType {
    BasicRetainingSoil("BasicRetainingSoil", 100),
    QualityRetainingSoil("QualityRetainingSoil", 150),
    DeluxeRetainingSoil("DeluxeRetainingSoil", 200),
    GrassStarter("GrassStarter", 100),
    PlantGrow("PlantGrow", 100),

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

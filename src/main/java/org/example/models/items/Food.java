package org.example.models.items;

public enum Food implements Item, Eatable {
    FriedEgg(50, 35, CookingRecipe.FriedEgg, "FriedEgg"),
    BakedFish(75, 100, CookingRecipe.BakedFish, "BakedFish"),
    Salad(113, 110, CookingRecipe.Salad, "Salad"),
    Omelet(100, 125, CookingRecipe.Omelet, "Omelet"),
    PumpkinPie(225, 385, CookingRecipe.PumpkinPie, "PumpkinPie"),
    Spaghetti(75, 120, CookingRecipe.Spaghetti, "Spaghetti"),
    Pizza(150, 300, CookingRecipe.Pizza, "Pizza"),
    Tortilla(50, 50, CookingRecipe.Tortilla, "Tortilla"),
    MakiRoll(100, 220, CookingRecipe.MakiRoll, "MakiRoll"),
    TripleShotEspresso(200, 450, CookingRecipe.TripleShotExspresso, "TripleShotEspresso"),
    Cookie(90, 140, CookingRecipe.Cookie, "Cookie"),
    HashBrowns(90, 120, CookingRecipe.HashBrown, "HashBrowns"),
    Pancakes(90, 80, CookingRecipe.Pancake, "Pancakes"),
    FruitSalad(263, 450, CookingRecipe.FruitSalad, "FruitSalad"),
    RedPlate(240, 400, CookingRecipe.RedPlate, "RedPlate"),
    Bread(50, 60, CookingRecipe.Bread, "Bread"),
    SalmonDinner(125, 300, CookingRecipe.SalmonDinner, "SalmonDinner"),
    VegetableMedley(165, 120, CookingRecipe.VegetableMedly, "VegetableMedley"),
    FarmerLunch(200, 150, CookingRecipe.FarmerLunch, "FarmerLunch"),
    SurvivalBurger(125, 180, CookingRecipe.SurvivalBurger, "SurvivalBurger"),
    DishOTheSea(150, 220, CookingRecipe.dishOSea, "DishOTheSea"),
    SeaFormPudding(175, 300, CookingRecipe.SeaformPuddin, "SeaFormPudding"),
    MinerTreat(125, 200, CookingRecipe.MinerTreat, "MinerTreat"),

//    Beer(50, 200, null, "Beer"),
//    Vineqar(13, 100, null, "Vineqar"),
//    Coffee(75, 150, null, "Coffee"),
//    Juice(0, 0, null, "Juice"), // based on its ingredient(x2,x2.25)
//    Mead(100, 300, null, "Mead"),
//    PaleAle(50, 300, null, "PaleAle"),
//    Wine(0, 0, null, "Wine"), // (x1.75,x3)
//    Cheese(100, 230, null, "Cheese"),
//    LargeCheese(100, 345, null, "LargeCheese"),
//    GoatCheese(100, 400, null, "GoatCheese"),
//    LargeGoatCheese(100, 600, null, "LargeGoatCheese"),
//    Honey(75, 350, null, "Honey"),
//    DriedMushrooms(50, 0, null, "DriedMushrooms"), // (50,x7.5+25)
//    DriedFruit(75, 0, null, "DriedFruit"), // (50,x7.5+25)
//    Raisins(125, 600, null, "Raisins"),
//    Pickles(0, 0, null, "Pickles"), // (x1.75,x2+50)
//    Jelly(0, 0, null, "Jelly"), // (x2,x2+50)

    ;
    static{
        CookingRecipe.values();
    }

    private int energyValue;
    private int sellPrice;
    private Buff buff;
    private CookingRecipe recipe;
    private String name;

    // TODO fill the buff as welll

    Food(int energyValue, int SellPrice, CookingRecipe recipe, String name) {
        this.energyValue = energyValue;
        this.sellPrice = SellPrice;
        this.recipe = recipe;
        this.name = name;
    }

    @Override
    public double getEnergy() {
        return energyValue;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public Buff getBuff() {
        return buff;
    }

    @Override
    public String getChar() {
        return "FF";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return sellPrice;
    }


    public CookingRecipe getRecipe() {
        return this.recipe;
    }

    public static Food parseFood(String food) {
        for (Food value : Food.values()) {
            if(value.getName().equalsIgnoreCase(food)) {
                return value;
            }
        }
        return null;
    }
}

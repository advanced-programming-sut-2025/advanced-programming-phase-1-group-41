package org.example.models.items;

public enum Food implements Item {
    FriedEgg(50, 35, null),
    BakedFish(75, 100, CookingRecipe.BakedFish),
    Salad(113, 110, null),
    Omelet(100, 125, null),
    PumpkinPie(225, 385, null),
    Spaghetti(75, 120, CookingRecipe.Spaghetti),
    Pizza(150, 300, CookingRecipe.Pizza),
    Tortilla(50, 50, null),
    MakiRoll(100, 220, null),
    TripleShotEspresso(200, 450, null),
    Cookie(90, 140, null),
    HashBrowns(90, 120, null),
    Pancakes(90, 80, null),
    FruitSalad(263, 450, null),
    RedPlate(240, 400, null),
    Bread(50, 60, CookingRecipe.Bread),
    SalmonDinner(125, 300, null),
    VegetableMedley(165, 120, null),
    FarmerLunch(200, 150, null),
    SurvivalBurger(125, 180, null),
    DishOTheSea(150, 220, null),
    SeaFormPudding(175, 300, null),
    MinerTreat(125, 200, null),
    Beer(50, 200, null),
    Vineqar(13, 100, null),
    Coffee(75, 150, null),
    Juice(0, 0, null), // based on its ingredient(x2,x2.25)
    Mead(100, 300, null),
    PaleAle(50, 300, null),
    Wine(0, 0, null), // (x1.75,x3)
    Cheese(100, 230, null),
    LargeCheese(100, 345, null),
    GoatCheese(100, 400, null),
    LargeGoatCheese(100, 600, null),
    Honey(75, 350, null),
    DriedMushrooms(50, 0, null), // (50,x7.5+25)
    DriedFruit(75, 0, null), // (50,x7.5+25)
    Raisins(125, 600, null),
    Pickles(0, 0, null), // (x1.75,x2+50)
    Jelly(0, 0, null), // (x2,x2+50)
    SmokedFish(0, 0, null) // (x1.5,x2)
    
    ;
    private int energyValue;
    private int sellPrice;
    private Buff buff;
    private CookingRecipe recipe;

    // TODO fill the buff as welll

    Food(int energyValue, int SellPrice, CookingRecipe recipe) {
        this.energyValue = energyValue;
        this.sellPrice = SellPrice;
        this.recipe = recipe;
    }

    public int getEnergyValue() {
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
        return "";
    }
}

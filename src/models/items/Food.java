package models.items;

public enum Food {
//    Egg(),
//    Fish(),
    FriedEgg(50, 35),
    BakedFish(75, 100),
    Salad(113, 110),
    Omelet(100, 125),
    PumpkinPie(225, 385),
    Spaghetti(75, 120),
    Pizza(150, 300),
    Tortilla(50, 50),
    MakiRoll(100, 220),
    TripleShotEspresso(200, 450),
    Cookie(90, 140),
    HashBrowns(90, 120),
    Pancakes(90, 80),
    FruitSalad(263, 450),
    RedPlate(240, 400),
    Bread(50, 60),
    SalmonDinner(125, 300),
    VegetableMedley(165, 120),
    FarmerLunch(200, 150),
    SurvivalBurger(125, 180),
    DishOTheSea(150, 220),
    SeaFormPudding(175, 300),
    MinerTreat(125, 200),
    // etc
    ;
    int energyValue;
    int sellPrice
    Buff buff;

    Food(int energyValue, int SellPrice) {
        this.energyValue = energyValue;
        this.sellPrice = SellPrice;
    }

}

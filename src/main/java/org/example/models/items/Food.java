package org.example.models.items;

public enum Food implements Item {
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
    Beer(50,200),
    Vineqar(13,100),
    Coffee(75,150),
    Juice(0,0),//based on its ingredient(x2,x2.25)
    Mead(100,300),
    PaleAle(50,300),
    Wine(0,0),//(x1.75,x3)
    Cheese(100,230),
    LargeCheese(100,345),
    GoatCheese(100,400),
    LargeGoatCheese(100,600),
    Honey(75,350),
    DriedMushrooms(50,0),//(50,x7.5+25)
    DriedFruit(75,0),//(50,x7.5+25)
    Raisins(125,600),
    Pickles(0,0),//(x1.75,x2+50)
    Jelly(0,0),//(x2,x2+50)
    SmokedFish(0,00),//(x1.5,x2)
    
    ;
    private int energyValue;
    private int sellPrice;
    private Buff buff;

    // TODO fill the buff as welll

    Food(int energyValue, int SellPrice) {
        this.energyValue = energyValue;
        this.sellPrice = SellPrice;
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

package models.items;

public enum Ores {
    Coal(150),
    Copper(75),
    Iron(150),
    Gold(400)
    ;

    int sellPrice;

    Ores(int SellPrice) {
        this.sellPrice = SellPrice;
    }

}

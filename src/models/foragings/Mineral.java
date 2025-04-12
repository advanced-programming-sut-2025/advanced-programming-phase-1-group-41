package models.foragings;

public enum Mineral implements Foraging{
    // TODO
    ;

    private String name;
    private int price;

    Mineral(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

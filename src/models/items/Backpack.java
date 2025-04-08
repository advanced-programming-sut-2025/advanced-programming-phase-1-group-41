package models.items;

public enum Backpack {
    Default(new Inventory(12)),
    Big(new Inventory(24)),
    Deluxe(new Inventory(100000)),;

    private Inventory inventory;

    Backpack(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

}

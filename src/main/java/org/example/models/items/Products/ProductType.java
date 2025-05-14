package org.example.models.items.Products;

import org.example.models.Colors;

public enum ProductType {
    ChickenEgg(50,"Egg",Colors.colorize(15,0,"oc") ),
    BigChickenEgg(95,"BigChickenEgg",Colors.colorize(15,0,"OC")),
    DuckEgg(95,"DuckEgg",Colors.colorize(64,0,"0d")),
    DuckFeather(250,"DuckFeather",Colors.colorize(64,0,"~~")),
    RabbitWool(340,"RabbitWool",Colors.colorize(15,0,"ǝR")),
    RabbitFoot(565,"RabbitFoot",Colors.colorize(15,0,"/\\")),
    DinoEgg(350,"DinoEgg",Colors.colorize(120,0,"0D")),
    CowMilk(125,"CowMilk",Colors.colorize(15,0,"uc")),
    BigCowMilk(190,"BigCowMilk",Colors.colorize(15,0,"UC")),
    GoatMilk(225,"GoatMilk",Colors.colorize(192,0,"ug")),
    BigGoatMilk(345,"BigGoatMilk",Colors.colorize(192,0,"UG")),
    SheepWool(340,"SheepWool",Colors.colorize(15,0,"ǝS")),
    PigTruffle(625,"PigTruffle",Colors.colorize(15,0,"db")),

    ;
    private int price;
    private String name;
    private String character;
    private ProductType(int price,String name,String character){
        this.price = price;
        this.name = name;
        this.character = character;

    }
    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public String getCharacter() {
        return character;
    }


    public static Product parseProductType(String productType){
        for (ProductType value : ProductType.values()) {
            if(value.getName().equalsIgnoreCase(productType)){
                return new Product(value);
            }
        }
        return null;
    }
}

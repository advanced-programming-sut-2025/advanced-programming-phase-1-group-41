package com.CEliconValley.models.items.Products;

import com.CEliconValley.models.items.Item;

public class Product implements Item {
    private double Quality;

    public Product(ProductType type) {
        this.productType = type;
    }
    private ProductType productType;

    public ProductType getProductType() {
        return productType;
    }

    @Override
    public String getChar() {
        return productType.getCharacter();
    }

    @Override
    public String getName() {
        return productType.getName();
    }

    @Override
    public double getPrice() {
        return productType.getPrice();
    }
    @Override
    public int getID() {
        switch (this.productType) {
            case ChickenEgg -> {
                return 10900;
            }
            case BigChickenEgg -> {
                return 10901;
            }
            case DuckEgg -> {
                return 10902;
            }
            case DuckFeather -> {
                return 10903;
            }
            case RabbitWool -> {
                return 10904;
            }
            case RabbitFoot -> {
                return 10905;
            }
            case DinoEgg -> {
                return 10906;
            }
            case CowMilk -> {
                return 10907;
            }
            case BigCowMilk -> {
                return 10908;
            }
            case GoatMilk -> {
                return 10909;
            }
            case BigGoatMilk -> {
                return 10910;
            }
            case SheepWool -> {
                return 10911;
            }

            case PigTruffle -> {
                return 11000;
            }
        }
        throw new IllegalStateException("Unknown item: " + this);
    }



}

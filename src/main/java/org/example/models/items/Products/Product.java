package org.example.models.items.Products;

import org.example.models.items.Item;

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

}

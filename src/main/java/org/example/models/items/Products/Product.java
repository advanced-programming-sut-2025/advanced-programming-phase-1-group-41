package org.example.models.items.Products;

import org.example.models.items.Item;

public class Product implements Item {

    public Product(ProductType type) {
        this.productType = type;
    }
    private ProductType productType;

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

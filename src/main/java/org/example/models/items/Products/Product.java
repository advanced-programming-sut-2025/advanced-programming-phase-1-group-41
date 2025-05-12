package org.example.models.items.Products;

import org.example.models.items.Item;

public class Product implements Item {
    private ProductType productType;

    @Override
    public String getChar() {
        return productType.getCharacter();
    }

    @Override
    public String getName() {
        return productType.getName();
    }
}

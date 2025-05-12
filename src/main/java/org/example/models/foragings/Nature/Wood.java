package org.example.models.foragings.Nature;

import org.example.models.items.Item;

public class Wood implements Item {
    @Override
    public String getName() {
        return "Wood";
    }
    // TODO add the wood in finder
    @Override
    public String getChar() {
        return "wd";
    }
}

package org.example.models.buildings;

import org.example.models.App;
import org.example.models.Colors;
import org.example.models.ObjectMap;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;

public class Refrigerator implements ObjectMap {
    @Override
    public String getChar() {
        return Colors.colorize(15,15,"[]");
    }

    @Override
    public String getName() {
        return "Refrigerator";
    }
    public final ArrayList<Slot> slots;

    public Refrigerator() {
        this.slots = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            this.slots.add(new Slot(null,0));
        }
    }

    public boolean removeFromRef(Item item, int quantity) {
        for(Slot slot : slots){
            if(slot.getItem() == null) continue;
            if(slot.getItem().equals(item)){
                if(slot.getQuantity() >= quantity){
                    slot.setQuantity(slot.getQuantity()-quantity);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    public int getEmptySlots(){
        int sum=0;
        for(Slot slot : slots){
            if(slot.getItem()==null||slot.getQuantity() == 0){
                sum++;
            }
        }
        return sum;
    }

    private boolean appendRef(Item item, int quantity){
        for (Slot slot : slots) {
            if(slot == null || slot.getItem() == null){
                continue;
            }
            if(item.getName().equals((slot.getItem().getName()))){
                slot.setQuantity(slot.getQuantity()+quantity);
                return true;
            }
        }
        return false;
    }

    public boolean addToRef(Item item, int quantity) {
        if(!appendRef(item, quantity)){
            for(Slot slot : slots){
                if(slot.getItem() == null || slot.getQuantity() == 0){
                    slot.setItem(item);
                    slot.setQuantity(quantity);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public Slot getSlotByItem(Item item){
        for(Slot slot : slots){
            if(slot == null || slot.getItem() == null) continue;
            if(slot.getItem().getName().equals(item.getName())){
                return slot;
            }
        }
        return null;
    }
}

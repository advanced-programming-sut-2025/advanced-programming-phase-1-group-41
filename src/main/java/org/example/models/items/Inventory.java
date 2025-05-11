package org.example.models.items;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<Slot> slots;
    public Backpack backpack=Backpack.Default;

    public Inventory() {
        setDefaultBag();
    }
    public void setDefaultBag() {
        for(int i = 0; i < backpack.getSize(); i++){
            slots.add(new Slot(null,0));
        }
    }
    public void Upgrade(){
        if(backpack.equals(Backpack.Default)){
            backpack = Backpack.Big;
            for(int i = 0; i < backpack.getSize()-Backpack.Default.getSize(); i++){
                slots.add(new Slot(null,0));
            }
        }
        else if(backpack.equals(Backpack.Big)){
            backpack = Backpack.Deluxe;
            for(int i = 0; i < backpack.getSize()-Backpack.Big.getSize(); i++){
                slots.add(new Slot(null,0));
            }
        }
    }
    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public boolean addToInventory(Item item, int quantity) {
        for(Slot slot : slots){
            if(slot.getItem() == null){
                slot.setItem(item);
                slot.setQuantity(quantity);
                return true;
            }
            else if(slot.getItem().equals(item)){
                slot.setQuantity(quantity+slot.getQuantity());
                return true;
            }
        }
        return false;
    }
    public boolean removeFromInventory(Item item, int quantity) {
        for(Slot slot : slots){
            if(slot.getItem().equals(item)){
                if(slot.getQuantity() > quantity){
                    slot.setQuantity(slot.getQuantity()-quantity);
                    return true;
                }
                else{
                    slots.remove(slot);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean removeFromInventory(Item item) {
        for(Slot slot : slots){
            if(slot.getItem().equals(item)){
                slots.remove(slot);
                return true;
            }
        }
        return false;
    }
}

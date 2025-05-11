package org.example.models.items;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Slot> slots=new ArrayList<>();
    private Backpack backpack=Backpack.Default;

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

    private boolean appendInventory(Item item, int quantity){
        for (Slot slot : slots) {
            if(slot.equals(item)){
                slot.setQuantity(slot.getQuantity()+quantity);
                return true;
            }
        }
        return false;
    }

    public boolean addToInventory(Item item, int quantity) {
        if(!appendInventory(item, quantity)){
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
    public boolean removeFromInventory(Item item, int quantity) {
        for(Slot slot : slots){
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
    public boolean removeFromInventory(Item item) {
        for(Slot slot : slots){
            if(slot.getItem().equals(item)){
                slot.setQuantity(0);
                return true;
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
}

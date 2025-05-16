package org.example.models.items;

import org.example.models.tools.*;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Slot> slots=new ArrayList<>();
    private Backpack backpack=Backpack.Default;
    private int lastGottenSumOfItemsPrice;
    public Inventory()
    {
        lastGottenSumOfItemsPrice=0;
        setDefaultBag();
        addToInventory(new TrashCan(), 1);
        addToInventory(new Pickaxe(), 1);
        // todo buy this
//        addToInventory(new FishingRod(), 1);
        addToInventory(new WateringCan(), 1);
        addToInventory(new Hoe(), 1);
        addToInventory(new Axe(), 1);
        addToInventory(new Scythe(), 1);
//        addToInventory(new Shear(), 1);
    }

    public void setDefaultBag() {
        for(int i = 0; i < backpack.getSize(); i++){
            slots.add(new Slot(null,0));
        }
    }
    public void upgradeBackpack(Backpack bp){
        if(bp.equals(Backpack.Large)){
            backpack = Backpack.Large;
            for(int i = 0; i < backpack.getSize()-Backpack.Default.getSize(); i++){
                slots.add(new Slot(null,0));
            }
        }
        else if(bp.equals(Backpack.Deluxe)){
            backpack = Backpack.Deluxe;
            for(int i = 0; i < backpack.getSize()-Backpack.Large.getSize(); i++){
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
            if(slot == null || slot.getItem() == null){
                continue;
            }
            if(item.getName().equals((slot.getItem().getName()))){
                if(Math.abs(item.getPrice() - slot.getItem().getPrice()) > 0.1){
                    continue;
                }
                slot.setQuantity(slot.getQuantity()+quantity);
                return true;
            }
        }
        return false;
    }

    private boolean appendInventory(Item item, int quantity, int price){
        for (Slot slot : slots) {
            if(slot == null || slot.getItem() == null){
                continue;
            }
            if(item.getName().equals((slot.getItem().getName()))){
                if(Math.abs(item.getPrice() - slot.getItem().getPrice()) > 0.1){
                    continue;
                }
                slot.setQuantity(slot.getQuantity()+quantity);
                slot.getItemsPrice().add(price);
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
            System.out.println("inventory is full..");
            return false;
        }
        return true;
    }

    public boolean addToInventory(Item item, int quantity, int price) {
        if(!appendInventory(item, quantity, price)){
            for(Slot slot : slots){
                if(slot.getItem() == null || slot.getQuantity() == 0){
                    slot.setItem(item);
                    slot.setQuantity(quantity);
                    slot.getItemsPrice().add(price);
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public double getCofOfTrashCan(){
        TrashCan tc = TrashCan.findTrashCan();
        if(tc != null){
            return switch (tc.getLevel()){
                case Default -> 0;
                case Copper -> 0.15;
                case Iron -> 0.30;
                case Gold -> 0.45;
                case Iridium -> 0.60;
            };
        }
        return 0;
    }

    public boolean removeFromInventory(Item item, int quantity) {
        for(Slot slot : slots){
            if(slot.getItem() == null || slot.getQuantity() == 0) continue;
            if(slot.getItem().getName().equals(item.getName())){
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

    public boolean removeFromInventory(Item item, int quantity, int price) {
        for(Slot slot : slots){
            if(slot.getItem() == null) continue;
            if(slot.getItem().getName().equals(item.getName())){
                if(slot.getQuantity() >= quantity){
                    slot.setQuantity(slot.getQuantity()-quantity);
                    for (int i = 0; i < quantity; i++) {
                        lastGottenSumOfItemsPrice+= (int)slot.getItemsPrice().poll();
                    }
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public int getTopPriceOfItem(Item item){
        for (Slot slot : slots) {
            if(slot.getItem() == null) continue;
            if(slot.getItem().getName().equals(item.getName())){
                Integer price = (Integer) slot.getItemsPrice().peek();
                if(price != null){
                    return price;
                }
                return -1;
            }
        }
        return -2;
    }


    public boolean removeFromInventory(Item item) {
        for(Slot slot : slots){
            if (slot.getItem() == null) continue;
            if(slot.getItem().getName().equals(item.getName())){
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

    public Slot getSlotByItem(Item item){
        for(Slot slot : slots){
            if(slot == null || slot.getItem() == null || slot.getQuantity() == 0) continue;
            if(slot.getItem().getName().equals(item.getName())){
                return slot;
            }
        }
        return null;
    }


    public int getLastGottenSumOfItemsPrice() {
        return lastGottenSumOfItemsPrice;
    }

    public void resetLastGottenSumOfItemsPrice() {
        this.lastGottenSumOfItemsPrice = 0;
    }
}

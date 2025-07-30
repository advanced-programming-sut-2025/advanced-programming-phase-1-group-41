package com.CEliconValley.common;

import com.CEliconValley.models.animals.Fish;
import com.CEliconValley.models.animals.FishType;
import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.CropType;
import com.CEliconValley.models.foragings.Fruit;
import com.CEliconValley.models.foragings.FruitType;
import com.CEliconValley.models.foragings.Nature.Mushroom;
import com.CEliconValley.models.foragings.Nature.Vegetable;
import com.CEliconValley.models.items.CraftableItem;
import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;
import com.CEliconValley.models.items.craftablemachines.*;
import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.HashMap;

@Embedded
public class MachineData {
    // testing..
    String craftableMachineName;
    int processTime;
    SlotData produceData;
    ArrayList<SlotData> slotsData;
    ArrayList<SlotData> receivedItemsData;
    HashMap<String, Object> metaData;
    public MachineData(Machine machine) {
        this.craftableMachineName = machine.getCraftableMachine().getName();
        this.processTime = machine.getProcessTime();
        this.produceData = new SlotData(machine.getProduce());
        slotsData = new ArrayList<>();
        receivedItemsData = new ArrayList<>();
        for (Slot slot : machine.getSlots()) {
            slotsData.add(new SlotData(slot));
        }
        for (Slot receivedItem : machine.getReceivedItems()) {
            receivedItemsData.add(new SlotData(receivedItem));
        }
        this.metaData = new HashMap<>();
        fillMetaData(machine);
    }

    private void fillMetaData(Machine machine) {
        if(machine instanceof CheesePress cp){
            metaData.put("which", cp.getWhich());
        }
        else if(machine instanceof Dehydrator dehydrator){
            metaData.put("fruit", dehydrator.getFruit() == null ? null : dehydrator.getFruit().getName());
            metaData.put("mushroom", dehydrator.getMushroom() == null ? null : dehydrator.getMushroom().getName());
            metaData.put("grape", dehydrator.getGrape() == null ? null : dehydrator.getGrape().getName());
            metaData.put("isGrape", dehydrator.isGrape());
        }
        else if(machine instanceof FishSmoker fs){
            metaData.put("fishType", fs.getFish().getFishType().getName());
        }
        else if(machine instanceof Keg keg){
            metaData.put("crop", keg.getCrop() == null ? null : keg.getCrop().getName());
            metaData.put("isWheat", keg.isWheat());
            metaData.put("isRice", keg.isRice());
            metaData.put("isCoffee", keg.isCoffee());
            metaData.put("isHops", keg.isHops());
            metaData.put("fruit", keg.getFruit() == null ? null : keg.getFruit().getName());
            metaData.put("honey", keg.getHoney() == null ? null : keg.getHoney().getName());
            metaData.put("vegetable", keg.getVegetable() == null ? null : keg.getVegetable().getName());
        }else if (machine instanceof MayoMachine mayo){
            metaData.put("which", mayo.getWhich());
        }else if(machine instanceof OilMaker o){
            metaData.put("isCorn", o.isCorn());
            metaData.put("isPigTruffle", o.isPigTruffle());
        }else if(machine instanceof PreserveJar pj){
            metaData.put("fruit", pj.getFruit() == null ? null : pj.getFruit().getName());
            metaData.put("crop", pj.getCrop() == null ? null : pj.getCrop().getName());
        }
    }

    public Machine getMachine() {
        ArrayList<Slot> slots = new ArrayList<>();
        for (SlotData sd : slotsData) {
            slots.add(sd.getSlot());
        }
        ArrayList<Slot> receivedItems = new ArrayList<>();
        for (SlotData sd : receivedItemsData) {
            receivedItems.add(sd.getSlot());
        }
        Slot produce = produceData.getSlot();
        switch(CraftableMachine.parseCraftable(this.craftableMachineName)){
            case BeeHouse -> {
                BeeHouse beeHouse = new BeeHouse(this.processTime, produce, receivedItems, slots);
                return beeHouse;
            }
            case CheesePress -> {
                int which = (int)metaData.get("which");
                CheesePress cheesePress = new CheesePress(this.processTime, produce,
                    receivedItems, slots, which);
                return cheesePress;
            }
            case Dehydrator -> {
                Fruit fruit = (String)metaData.get("fruit") == null ? null : new Fruit(FruitType.parseFruitType((String)metaData.get("fruit")));
                Mushroom mushroom = (String)metaData.get("mushroom") == null ? null : Mushroom.parseMushroom((String)metaData.get("mushroom"));
                Crop grape = (String)metaData.get("grape") == null ? null : new Crop(CropType.parseCropType((String)metaData.get("grape")));
                boolean isGrape = (boolean) metaData.get("isGrape");
                Dehydrator dehydrator = new Dehydrator(this.processTime, produce, receivedItems, slots, fruit, mushroom, grape, isGrape);
                return dehydrator;
            }
            case FishSmoker -> {
                Fish fish = new Fish(FishType.parseFishType((String)metaData.get("fishType")));
                FishSmoker fishSmoker = new FishSmoker(this.processTime, produce, receivedItems, slots, fish);
                return fishSmoker;
            }
            case Furnace -> {
                Furnace furnace = new Furnace(this.processTime, produce, receivedItems, slots);
                return furnace;
            }
            case Keg -> {
                Crop crop = (String) metaData.get("crop") == null ? null : new Crop(CropType.parseCropType((String)metaData.get("crop")));
                boolean isWheat = (boolean) metaData.get("isWheat");
                boolean isRice = (boolean) metaData.get("isRice");
                boolean isCoffee = (boolean) metaData.get("isCoffee");
                boolean isHops = (boolean) metaData.get("isHops");
                Fruit fruit = (String) metaData.get("fruit") == null ? null : new Fruit(FruitType.parseFruitType((String)metaData.get("fruit")));
                CraftableItem honey = (String) metaData.get("honey") == null ? null : CraftableItem.Honey;
                Vegetable vegetable = (String) metaData.get("vegetable") == null ? null : Vegetable.parseVegetable((String)metaData.get("vegetable"));
                Keg keg = new Keg(this.processTime, produce, receivedItems, slots, crop, isWheat, isRice, isCoffee, isHops, fruit, honey, vegetable);
                return keg;
            }
            case CharcoalKiln -> {
                Kiln kiln = new Kiln(this.processTime, produce, receivedItems, slots);
                return kiln;
            }
            case Loom -> {
                Loom loom = new Loom(this.processTime, produce, receivedItems, slots);
                return loom;
            }
            case MayonnaiseMachine -> {
                int which = (int)metaData.get("which");
                MayoMachine mayoMachine = new MayoMachine(this.processTime, produce, receivedItems, slots, which);
                return mayoMachine;
            }
            case OilMaker -> {
                boolean isCorn = (boolean) metaData.get("isCorn");
                boolean isPigTruffle = (boolean) metaData.get("isPigTruffle");
                OilMaker oilMaker = new OilMaker(this.processTime, produce, receivedItems, slots, isCorn, isPigTruffle);
                return oilMaker;
            }
            case PreservesJar -> {
                Fruit fruit = (String) metaData.get("fruit") == null ? null : new Fruit(FruitType.parseFruitType((String)metaData.get("fruit")));
                Crop crop = (String) metaData.get("crop") == null ? null : new Crop(CropType.parseCropType((String)metaData.get("crop")));
                PreserveJar preserveJar = new PreserveJar(this.processTime, produce, receivedItems, slots, fruit, crop);
                return preserveJar;
            }
        }
        return null;
    }
}

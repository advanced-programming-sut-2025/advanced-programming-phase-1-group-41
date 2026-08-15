package com.CEliconValley.models.items.craftablemachines;

import com.CEliconValley.models.items.CraftableMachine;
import com.CEliconValley.models.items.Slot;

import java.util.ArrayList;

public abstract class Machine {
    protected CraftableMachine craftableMachine;
    protected int processTime;
    protected int initTime;
    protected Slot produce;
    protected ArrayList<Slot> slots;
    protected ArrayList<Slot> receivedItems;
    protected boolean start;
    public Machine(int processTime, CraftableMachine craftableMachine) {
        this.processTime = processTime;
        this.initTime = processTime;
        this.craftableMachine = craftableMachine;
        this.produce = null;
        this.slots = new ArrayList<>();
        this.receivedItems = new ArrayList<>();
        this.start = false;
    }


    public Machine(CraftableMachine craftableMachine, int processTime,
                   Slot produce, ArrayList<Slot> receivedItems, ArrayList<Slot> slots) {
        this.craftableMachine = craftableMachine;
        this.processTime = processTime;
        this.produce = produce;
        this.receivedItems = receivedItems;
        this.slots = slots;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void decreaseProcessTime() {
        processTime--;
    }

    public boolean suffice() {
        for (int i = 0; i < slots.size(); i++) {
            if(slots.get(i).getQuantity() != this.receivedItems.get(i).getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public Slot getProduce() {
        return produce;
    }

    public abstract void setProduce();

    public CraftableMachine getCraftableMachine() {
        return craftableMachine;
    }

    public ArrayList<Slot> getReceivedItems() {
        return receivedItems;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }


    public int getInitTime() {
        return initTime;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }
}

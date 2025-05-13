package org.example.models.items.craftablemachines;

import org.example.models.items.CraftableMachine;
import org.example.models.items.Slot;

public abstract class Machine {
    protected CraftableMachine craftableMachine;
    protected int processTime;
    protected Slot produce;
    public Machine(int processTime, CraftableMachine craftableMachine) {
        this.processTime = processTime;
        this.craftableMachine = craftableMachine;
        this.produce = null;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void decreaseProcessTime() {
        processTime--;
    }

    public abstract boolean suffice();

    public Slot getProduce() {
        return produce;
    }

    public abstract void setProduce();

    public CraftableMachine getCraftableMachine() {
        return craftableMachine;
    }
}

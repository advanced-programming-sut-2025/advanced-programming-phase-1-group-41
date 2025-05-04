package org.example.models;

public class Cell {
    private ObjectMap objectMap;
    private int x;
    private int y;

    public Cell(ObjectMap objectMap, int x, int y) {
        this.objectMap = objectMap;
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public ObjectMap getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(ObjectMap objectMap) {
        this.objectMap = objectMap;
    }
}

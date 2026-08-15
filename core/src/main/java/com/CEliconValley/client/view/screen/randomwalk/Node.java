package com.CEliconValley.client.view.screen.randomwalk;


public class Node implements Comparable<Node> {
    public int x, y;
    public double energyCost;
    public int turns;
    public Node parent;
    public Node(int x, int y, double energyCost, int turns, Node parent) {
        this.x = x;
        this.y = y;
        this.energyCost = energyCost;
        this.turns = turns;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.energyCost, other.energyCost);
    }

    @Override
    public String toString() {
        return "Node{" +
            "x=" + x +
            ", y=" + y +
            ", energyCost=" + energyCost +
            ", turns=" + turns +
            '}';
    }

    public int getDirection(){
        if(this.parent == null){
            return 0;
        }
        if(this.parent.x == this.x && this.parent.y == this.y - 1){
            return 1;
        }
        if(this.parent.x == this.x - 1 && this.parent.y == this.y){
            return 2;
        }
        if(this.parent.x == this.x && this.parent.y == this.y + 1) {
            return 3;
        }
        if(this.parent.x == this.x + 1 && this.parent.y == this.y){
            return 4;
        }
        return 0;
    }
}


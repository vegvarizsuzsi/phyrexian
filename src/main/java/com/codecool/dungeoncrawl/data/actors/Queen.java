package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;

public class Queen  extends  Actor{
    public Queen(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "queen";
    }
}

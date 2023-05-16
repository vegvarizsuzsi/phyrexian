package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;

public class Key extends Item {
    public Key(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "key";
    }
}

package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Actor;

public class Heart extends Item {
    public Heart(Cell cell) {
        super(cell);
    }


    @Override
    public String getTileName() {
        return "heart";
    }

}

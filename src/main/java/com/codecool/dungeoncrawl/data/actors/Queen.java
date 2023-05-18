package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.items.Heart;

public class Queen  extends  Actor{
    public Queen(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "queen";
    }

    public void convertToHeart() {
        Cell currentCell = getCell();
        Heart heart = new Heart(currentCell);
        currentCell.setItem(heart);
        currentCell.setActor(null);
    }

}

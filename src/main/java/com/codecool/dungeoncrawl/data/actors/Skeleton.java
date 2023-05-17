package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;

public class Skeleton extends Actor {
    private int movementDirection = 1;
    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void moveHorizontally() {
        int nextDx = movementDirection; // Move horizontally based on the current direction

        Cell nextCell = cell.getNeighbor(nextDx, 0); // Use dy = 0 to move horizontally
        if (nextCell.getType() == CellType.WALL) {
            // The next cell is a wall, reverse the movement direction
            movementDirection *= -1;
            return;
        }

        if (nextCell.getActor() != null && nextCell.getActor() != this) {
            // The next cell contains an actor that is not the boss, reverse the movement direction
            movementDirection *= -1;
            return;
        }

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }
}

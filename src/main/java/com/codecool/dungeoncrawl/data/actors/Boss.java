package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss  extends Actor implements Movement {
    private int movementDirection = 1;

    public Boss(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "boss";
    }
    public void move(int dx, int dy){

    }

    public void moveHorizontally() {
        List<Cell> neighboringCells = getNeighboringCells();

        if (neighboringCells.isEmpty()) {
            // No neighboring cells available, return
            return;
        }
        if(cell.getType() == CellType.CLOSED_DOOR)
            return;

        // Randomly select a neighboring cell
        Random random = new Random();
        Cell nextCell = neighboringCells.get(random.nextInt(neighboringCells.size()));

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    private List<Cell> getNeighboringCells() {
        List<Cell> neighboringCells = new ArrayList<>();

        // Iterate over all neighboring cells
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    Cell neighbor = cell.getNeighbor(dx, dy);
                    if (isValidCell(neighbor)) {
                        neighboringCells.add(neighbor);
                    }
                }
            }
        }

        return neighboringCells;
    }

    private boolean isValidCell(Cell cell) {
        return cell != null && cell.getType() != CellType.WALL && (cell.getActor() == null && cell.getType() != CellType.CLOSED_DOOR || cell.getActor() == this) ;
    }
}

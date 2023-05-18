package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Actor implements Movement {
    private int movementDirection = 1;
    private int health = 11;
    private int damage = 2;

    public Boss(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "boss";
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void move(int dx, int dy) {
        // Boss movement logic (if any)
    }

    public void moveHorizontally() {
        List<Cell> neighboringCells = getNeighboringCells();

        if (neighboringCells.isEmpty()) {
            return;
        }
        if (cell.getType() == CellType.CLOSED_DOOR) {
            return;
        }

        Random random = new Random();
        Cell nextCell = neighboringCells.get(random.nextInt(neighboringCells.size()));
        if (health <= 0) {
            cell.setActor(null);
            return;
        }

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    private List<Cell> getNeighboringCells() {
        List<Cell> neighboringCells = new ArrayList<>();

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
        return cell != null && cell.getType() != CellType.WALL && (cell.getActor() == null && cell.getType() != CellType.CLOSED_DOOR || cell.getActor() == this);
    }

    public void decreaseHealth(int playerDamage) {
        health -= playerDamage;
    }
}

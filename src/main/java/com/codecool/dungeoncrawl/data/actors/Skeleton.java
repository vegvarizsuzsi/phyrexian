package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;

public class Skeleton extends Actor {
    private int health = 5;
    private int damage = 1;
    private int movementDirection = 1;

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    public void moveHorizontally() {
        int nextDx = movementDirection;

        Cell nextCell = cell.getNeighbor(nextDx, 0);
        if (nextCell.getType() == CellType.WALL) {

            movementDirection *= -1;
            return;
        }

        if (nextCell.getActor() != null && nextCell.getActor() != this) {

            movementDirection *= -1;
            return;
        }
        if (health <= 0) {
            cell.setActor(null);
            return;
        }

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
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

    public void decreaseHealth(int playerDamage) {
        health -= playerDamage;
    }
}

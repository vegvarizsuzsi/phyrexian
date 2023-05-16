package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private List<Item> inventory;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.inventory = new ArrayList<>();
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.WALL) {
            // The next cell is a wall, the player cannot move there
            return;
        }

        if (nextCell.getActor() != null) {
            // The next cell is an actor, the player cannot move there
            return;
        }
        if (nextCell.getItem() != null) {
            Item item = nextCell.getItem();
            pickUpItem(item);
            nextCell.setItem(null);
        }

        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public void pickUpItem(Item item) {
        inventory.add(item);
        health += 10;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}

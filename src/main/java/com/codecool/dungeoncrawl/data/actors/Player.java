package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Movement;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor implements Movement {

    private int health = 10;
    private List<Item> inventory;
//    public Player(Cell cell) {
//        super(cell);
//    }

    public Player(Cell cell) {
        super(cell);
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }

    @Override
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


}

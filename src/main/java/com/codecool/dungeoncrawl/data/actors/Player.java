package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Movement;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.logic.Game;
import com.codecool.dungeoncrawl.logic.GameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Actor implements Movement {

    private int health = 10;
    private String name;
    private List<Item> inventory;
//    public Player(Cell cell) {
//        super(cell);
//    }

    public Player(Cell cell){
        super(cell);
        this.inventory = new ArrayList<>();

    }
    public Player(Cell cell, String name) {
        super(cell);
        this.inventory = new ArrayList<>();
        this.name = name;
    }

    public String getTileName() {
        return "player";
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);

        if ((Arrays.asList("developer1", "developer2").contains("developer1")) || (Arrays.asList("developer1", "developer2").contains("developer2"))){
                if (nextCell.getType() == CellType.WALL) {
                nextCell.setType(CellType.DEVWALL);
            }

//            if (Arrays.asList("developer1", "developer2").contains(getName())) {
//                if (nextCell.getType() == CellType.WALL) {
//                    nextCell.setType(CellType.DEVWALL);
//
//                }

        } else {
            if (nextCell.getType() == CellType.WALL) {
                // The next cell is a wall, the player cannot move there
                return;
            }
        }
        if (nextCell.getType() == CellType.CLOSED_DOOR) {
            // The next cell is a closed door, check if the player has a key
            if (!hasKeyInInventory()) {
                // The player does not have a key, cannot pass through the closed door
                return;
            } else {
                // Remove the key from the player's inventory
                removeKeyFromInventory();
                // Change the closed door to an open door
                nextCell.setType(CellType.FLOOR);
            }
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

    private boolean hasKeyInInventory() {
        // Check if the player has a key in their inventory
        for (Item item : inventory) {
            if (item.getTileName().equals("key")) {
                return true;
            }
        }
        return false;
    }

    private void removeKeyFromInventory() {
        // Remove a key from the player's inventory
        for (Item item : inventory) {
            if (item.getTileName().equals("key")) {
                inventory.remove(item);
                break; // Stop iterating once a key is removed
            }
        }
    }


    public List<Item> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

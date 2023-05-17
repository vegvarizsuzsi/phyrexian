package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.Movement;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor implements Movement {

    private int health = 10;
    private int damage = 5;
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
            if (nextCell.getActor() instanceof Boss) {
                Boss boss = (Boss) nextCell.getActor();
                attack(boss);
            } else {
                return;
            }

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
    private void attack(Boss boss) {
        int playerDamage = calculatePlayerDamage();
        int monsterDamage = 5;

        boss.decreaseHealth(playerDamage);
        decreaseHealth(monsterDamage);

        if (boss.getHealth() <= 0) {
            // Monster is defeated
            // Implement the logic for the monster's defeat
            boss.setHealth(0);
            cell.setActor(null); // Remove the monster from the cell
        }

        if (getHealth() <= 0) {
            // Player is defeated
            // Implement the logic for the player's defeat
        }
    }

    private int calculatePlayerDamage() {
        int baseDamage = 2; // Base damage inflicted by the player
        int weaponDamage = 0; // Damage added by the player's weapon (if any)



        return baseDamage + weaponDamage;
    }

    private void decreaseHealth(int damage) {
        int currentHealth = getHealth();
        int newHealth = currentHealth - damage;
        setHealth(newHealth);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


}

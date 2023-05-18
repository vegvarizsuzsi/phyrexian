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
            return;
        }
        if (nextCell.getType() == CellType.CLOSED_DOOR) {
            if (!hasKeyInInventory()) {

                return;
            } else {
                removeKeyFromInventory();
                nextCell.setType(CellType.FLOOR);
            }
        }

        if (nextCell.getActor() != null) {
            if (nextCell.getActor() instanceof Boss) {
                Boss boss = (Boss) nextCell.getActor();
                attack(boss);
            }else if (nextCell.getActor() instanceof Skeleton) {
                Skeleton skeleton = (Skeleton) nextCell.getActor();
                attack(skeleton);
            }
            else {
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
        for (Item item : inventory) {
            if (item.getTileName().equals("key")) {
                return true;
            }
        }
        return false;
    }

    private void removeKeyFromInventory() {
        for (Item item : inventory) {
            if (item.getTileName().equals("key")) {
                inventory.remove(item);
                break;
            }
        }
    }


    public List<Item> getInventory() {
        return inventory;
    }
    private void attack(Actor actor) {
        int playerDamage = calculatePlayerDamage();
        if (actor instanceof Boss) {
            Boss boss = (Boss) actor;

            int bossDamage = boss.getDamage();

            boss.decreaseHealth(playerDamage);
            decreaseHealth(bossDamage);

            if (boss.getHealth() <= 0) {
                boss.setHealth(0);
                cell.setActor(null);
            }
        } else if (actor instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) actor;
            int skeletonDamage = skeleton.getDamage();

            skeleton.decreaseHealth(playerDamage);
            decreaseHealth(skeletonDamage);

            if (skeleton.getHealth() <= 0) {
                skeleton.setHealth(0);
                cell.setActor(null);
            }
        }
    }

    private int calculatePlayerDamage() {
        int baseDamage = 5;
        int weaponDamage = 0;

        for (Item item : inventory) {
            if (item.getTileName().equals("sword")) {
                weaponDamage = 10;
                break;
            }
        }

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

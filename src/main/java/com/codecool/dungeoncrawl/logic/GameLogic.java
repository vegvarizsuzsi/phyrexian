package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.List;

public class GameLogic {
    private GameMap map;
    private Player player;
    private boolean gameOver;

    public GameLogic() {
        this.map = MapLoader.loadMap();
        this.gameOver = false;

    }
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public void checkGameOverConditions() {
        if (map.getPlayer().getHealth() <= 0) {
            setGameOver();
        }

    }

    public double getMapWidth() {
        return map.getWidth();
    }

    public double getMapHeight() {
        return map.getHeight();
    }


    public void setup() {
    }

    public Cell getCell(int x, int y) {
        return map.getCell(x, y);
    }

    public String getPlayerHealth() {
        return Integer.toString(map.getPlayer().getHealth());
    }

    public List<Item> getPlayerInventory() {
        Actor player = map.getPlayer();
        if (player instanceof Player)
            return ((Player) player).getInventory();
        return null;

    }

    public GameMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return map.getPlayer();
    }

}

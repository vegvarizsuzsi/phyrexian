package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.CellType;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.data.items.Heart;
import com.codecool.dungeoncrawl.data.items.Key;
import com.codecool.dungeoncrawl.data.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'g':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.setSkeleton(new Skeleton(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case '$':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case '&':
                            cell.setType(CellType.FLOOR);
                            new Heart(cell);
                            break;
                        case 'Ł':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case '%':
                            cell.setType(CellType.FLOOR);
                            new Queen(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            map.setBoss(new Boss(cell));
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}

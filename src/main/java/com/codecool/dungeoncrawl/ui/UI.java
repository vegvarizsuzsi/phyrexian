package com.codecool.dungeoncrawl.ui;

import com.codecool.dungeoncrawl.data.Cell;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.logic.GameLogic;
import com.codecool.dungeoncrawl.ui.elements.MainStage;
import com.codecool.dungeoncrawl.ui.keyeventhandler.KeyHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Set;

public class UI {
    private Canvas canvas;
    private GraphicsContext context;
    private MainStage mainStage;
    private GameLogic logic;
    private Set<KeyHandler> keyHandlers;
    private TextField nameTextField;


    public UI(GameLogic logic, Set<KeyHandler> keyHandlers) {
        this.canvas = new Canvas(
                logic.getMapWidth() * Tiles.TILE_WIDTH,
                logic.getMapHeight() * Tiles.TILE_WIDTH);
        this.logic = logic;
        this.context = canvas.getGraphicsContext2D();
        this.mainStage = new MainStage(canvas);
        this.keyHandlers = keyHandlers;
        this.nameTextField = new TextField();


        this.nameTextField.setOnAction(event -> {
            String playerName = nameTextField.getText().trim();
            if (!playerName.isEmpty()) {

                mainStage.getCanvas().requestFocus();

                logic.setup();
                refresh();

            }
            event.consume();
        });
    }

    public void setUpPane(Stage primaryStage) {
        Scene scene = mainStage.getScene();
        primaryStage.setScene(scene);
        Platform.runLater(() -> nameTextField.requestFocus());

        logic.setup();
        mainStage.setPlayerName(logic.getPlayer());
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (!logic.isGameOver()) {
            for (KeyHandler keyHandler : keyHandlers) {
                keyHandler.perform(keyEvent, logic.getMap());
            }
            logic.checkGameOverConditions();
            refresh();
        }
    }


    public void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < logic.getMapWidth(); x++) {
            for (int y = 0; y < logic.getMapHeight(); y++) {
                Cell cell = logic.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if ((cell.getItem() != null)) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
            if (logic.isGameOver()) {
                showGameOverAlert();
            }
        }
        mainStage.setHealthLabelText(logic.getPlayerHealth());
        mainStage.setinventoryLabelText(logic.getPlayerInventory());

    }
    private  void showGameOverAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over");
        alert.setContentText("You have lost the game.");
        alert.showAndWait();
    }

}

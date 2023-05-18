package com.codecool.dungeoncrawl.ui.elements;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.List;

public class StatusPane {
    public static final int RIGHT_PANEL_WIDTH = 200;
    public static final int RIGHT_PANEL_PADDING = 10;
    private GridPane ui;
    private Label healthTextLabel;
    private Label healthValueLabel;
    private Label inventoryTextLabel;
    private Label inventoryValueLabel;
    private Label nameLabel;
    private TextField nameTextField;

    public StatusPane() {
        ui = new GridPane();
        healthTextLabel = new Label("Health: ");
        healthValueLabel = new Label();
        inventoryTextLabel = new Label("Inventory: ");
        inventoryValueLabel = new Label();
        nameLabel = new Label("Name: ");
        nameTextField = new TextField();

    }

    public void addPlayerName(Player player) {
        nameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                player.setName(nameTextField.getText());
                nameTextField.getParent().requestFocus();
                event.consume();
            }
        });
    }

    private void showNameErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Name");
        alert.setHeaderText("Invalid Name");
        alert.setContentText("Please enter a valid name.");
        alert.showAndWait();
    }

    public BorderPane build() {

        ui.setPrefWidth(RIGHT_PANEL_WIDTH);
        ui.setPadding(new Insets(RIGHT_PANEL_PADDING));

        ui.add(healthTextLabel, 0, 0);
        ui.add(healthValueLabel, 1, 0);
        ui.add(inventoryTextLabel, 0, 1);
        ui.add(inventoryValueLabel, 1, 1);
        ui.add(nameLabel, 0, 2);
        ui.add(nameTextField, 1, 2);

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(ui);

        return borderPane;


    }

    public void setHealthValue(String text) {
        healthValueLabel.setText(text);
    }

    public void setInventoryValue(List<Item> playerInventory) {
        StringBuilder inventoryText = new StringBuilder();
        for (Item item : playerInventory) {
            inventoryText.append(item.getTileName()).append(",");
        }
        inventoryValueLabel.setText(inventoryText.toString());
    }

    public String getPlayerName() {
        return nameTextField.getText();
    }
}

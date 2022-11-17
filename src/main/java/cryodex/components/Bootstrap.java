package cryodex.components;

import cryodex.modules.xwing.XWingPlayer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Bootstrap {
    public Label H1(String text) {
        return createLabel(text, "h1");
    }
    public Label H2(String text) {
        return createLabel(text, "h2");
    }
    public Label H3(String text) {
        return createLabel(text, "h3");
    }
    public Label H4(String text) {
        return createLabel(text, "h4");
    }
    public Label H5(String text) {
        return createLabel(text, "h5");
    }
    public Label DefaultLabel(String text) {
        return createLabel(text, "lbl-default");
    }
    public Label PrimaryLabel(String text) {
        return createLabel(text, "lbl-primary");
    }
    public Label SuccessLabel(String text) {
        return createLabel(text, "lbl-success");
    }
    public Label InfoLabel(String text) {
        return createLabel(text, "lbl-info");
    }
    public Label WarningLabel(String text) {
        return createLabel(text, "lbl-warning");
    }
    public Button DefaultButton(String text) {
        return createButton(text, "btn-default");
    }
    public Button PrimaryButton(String text) {
        return createButton(text, "btn-primary");
    }
    public Button SuccessButton(String text) {
        return createButton(text, "btn-success");
    }
    public Button InfoButton(String text) {
        return createButton(text, "btn-info");
    }
    public Button WarningButton(String text) {
        return createButton(text, "btn-warning");
    }
    public Button DangerButton(String text) {
        return createButton(text, "btn-danger");
    }

    public TextField DefaultTextField(String text) {
        return createTextField(text, "lead");
    }
    public TextField DefaultTextField() {
        return createTextField("", "lead");
    }
    public static final double SPACING = 14;
    public VBox VBox(Node... nodes) {
        var vBox = new VBox(nodes);
        vBox.setSpacing(SPACING);
        return vBox;
    }
    public VBox VBox() {
        var vBox = new VBox();
        vBox.setSpacing(SPACING);
        return vBox;
    }
    public HBox HBox(Node... nodes) {
        var hBox = new HBox(nodes);
        hBox.setSpacing(SPACING);
        return hBox;
    }
    public ComboBox<XWingPlayer.Faction> Combobox() {
        var comboBox = new ComboBox<XWingPlayer.Faction>();
        comboBox.getStyleClass().add("btn-default");
        return comboBox;

    }
    private Button createButton(String text, String style) {
        var button = new Button(text);
        button.getStyleClass().add(style);
        button.getStyleClass().add("btn-sm");
        return button;
    }
    private Label createLabel(String text, String style) {
        var label = new Label(text);
        label.getStyleClass().add(style);
        return label;
    }

    private TextField createTextField(String text, String style) {
        var textField = new TextField(text);
        textField.getStyleClass().add(style);
        return textField;
    }
}

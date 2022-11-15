package cryodex.components;

import javafx.scene.control.Label;

public class Header {
    public Label H1(String text) {
        return create(text, "h1");
    }
    public Label H2(String text) {
        return create(text, "h2");
    }
    public Label H3(String text) {
        return create(text, "h3");
    }
    public Label H4(String text) {
        return create(text, "h4");
    }
    public Label H5(String text) {
        return create(text, "h5");
    }
    private Label create(String text, String style) {
        var label = new Label(text);
        label.getStyleClass().add(style);
        return label;
    }
}

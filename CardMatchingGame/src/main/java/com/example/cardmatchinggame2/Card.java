package com.example.cardmatchinggame2;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {
    private final Text text = new Text();
    private boolean isFlipped = false;
    private boolean isMatched = false;
    private final String value;

    public Card(String value, GameController controller) {
        this.value = value;

        Rectangle border = new Rectangle(100, 100);
        border.setFill(Color.LIGHTGRAY);
        border.setStroke(Color.BLACK);

        text.setText(value);
        text.setVisible(false);

        getChildren().addAll(border, text);

        setOnMouseClicked(e -> {
            if (!isFlipped && !isMatched) {
                flip();
                controller.cardClicked(this);
            }
        });
    }

    public void flip() {
        isFlipped = !isFlipped;
        text.setVisible(isFlipped);
    }

    public String getValue() {
        return value;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public boolean isMatched() {
        return isMatched;
    }
}
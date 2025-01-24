package panel.game.ball;

import java.awt.*;

//ball의 색깔 지정
public enum InkBallColor {
    GRAY(Color.GRAY),
    ORANGE(java.awt.Color.ORANGE),
    BLUE(java.awt.Color.BLUE),
    GREEN(java.awt.Color.GREEN),
    YELLOW(java.awt.Color.YELLOW);

    private final Color color;

    InkBallColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static InkBallColor fromIndex(int index) {
        return values()[index];
    }
}
package stage.component;

import panel.game.ball.Ball;

import javax.swing.*;
import java.awt.*;

public class Empty implements Component{
    private final int row, col;
    private final int left, right, top, bottom;

    public Empty(int row, int col){
        this.row = row;
        this.col = col;

        this.left = col * cellSize + offset;
        this.right = this.left + cellSize - offset;
        this.top = row * cellSize + offset;
        this.bottom = this.top + cellSize - offset;
    }

    //충돌 X
    @Override
    public int collide(Ball ball) {
        return 0;
    }

    //그리기
    @Override
    public void render(Graphics2D g2d) {
        ImageIcon emptyImage = new ImageIcon(getClass().getResource("/res/background.png"));
        g2d.drawImage(emptyImage.getImage(), col * cellSize + offset, row * cellSize + offset, cellSize - offset, cellSize - offset, null);
    }
}

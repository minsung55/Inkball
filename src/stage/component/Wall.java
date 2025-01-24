package stage.component;

import java.awt.*;

import panel.game.ball.Ball;

import javax.swing.*;

public class Wall implements Component{
    private ImageIcon wallImage = new ImageIcon("C:\\Users\\Lee\\Desktop\\Inkball\\Main\\src\\wall.jpg");
    private final int row, col;
    private final int left, right, top, bottom;

    public Wall(int row, int col){
        this.row = row;
        this.col = col;

        this.left = col * cellSize + offset;
        this.right = this.left + cellSize - offset;
        this.top = row * cellSize + offset;
        this.bottom = this.top + cellSize - offset;
    }

    //충돌 시, xspeed, yspeed 반전
    @Override
    public int collide(Ball ball) {
        int x = ball.getX();
        int y = ball.getY();
        int radius = ball.getRadius();
        int xSpeed = ball.getXSpeed();
        int ySpeed = ball.getYSpeed();

        if (x + radius > left && x - radius < right && y + radius > top && y - radius < bottom) {
            if (x < left && xSpeed > 0) {
                ball.setXSpeed(-xSpeed);
            } else if (x > right && xSpeed < 0) {
                ball.setXSpeed(-xSpeed);
            }
            if (y < top && ySpeed > 0) {
                ball.setYSpeed(-ySpeed);
            } else if (y > bottom && ySpeed < 0) {
                ball.setYSpeed(-ySpeed);
            }
        }
        return 0;
    }

    //그리기
    @Override
    public void render(Graphics2D g2d) {

        ImageIcon wallImage = new ImageIcon(getClass().getResource("/res/wall.png"));
        g2d.drawImage(wallImage.getImage(), col * cellSize + offset, row * cellSize + offset, cellSize - offset, cellSize - offset, null);
    }
}

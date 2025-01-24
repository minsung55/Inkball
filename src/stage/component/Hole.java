package stage.component;

import panel.game.ball.Ball;
import panel.game.ball.InkBallColor;

import javax.swing.*;
import java.awt.*;

public class Hole implements Component{
	private final int row, col;
	private final int left, right, top, bottom;
	private int color;

	public Hole(int row, int col, int color){
		this.row = row;
		this.col = col;
		this.color = color;

		this.left = col * cellSize + offset;
		this.right = this.left + cellSize - offset;
		this.top = row * cellSize + offset;
		this.bottom = this.top + cellSize - offset;
	}


	//색깔 일치 O -> 100점
	//색깔 일치 X -> -100점
	@Override
	public int collide(Ball ball) {
		int x = ball.getX();
		int y = ball.getY();

		if (x > left && x < right && y > top && y < bottom) {
			System.out.println("panel.game.ball.Ball entered a hole!" + String.valueOf(color) + String.valueOf(ball.getColorIndex()));
			if(ball.getColorIndex() == this.color) return 100;
			else return -100;
		}
		return 0;
	}

	//그리기
	@Override
	public void render(Graphics2D g2d) {
		ImageIcon holeImage = switch (color) {
			case 0 -> new ImageIcon(getClass().getResource("/res/gray_hole.png"));
			case 1 -> new ImageIcon(getClass().getResource("/res/orange_hole.png"));
			case 2 -> new ImageIcon(getClass().getResource("/res/blue_hole.png"));
			case 3 -> new ImageIcon(getClass().getResource("/res/green_hole.png"));
			case 4 -> new ImageIcon(getClass().getResource("/res/yellow_hole.png"));
			default -> null;
		};

		g2d.drawImage(holeImage.getImage(), col * cellSize + offset, row * cellSize + offset, cellSize - offset, cellSize - offset, null);
	}
}


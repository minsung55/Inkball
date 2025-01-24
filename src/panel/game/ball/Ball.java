package panel.game.ball;

import panel.game.line.LinePoint;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

//Ball 객체
public class Ball {
	private int x, y, xSpeed, ySpeed;
	private final int BALL_RADIUS = 12;
	private int colorIndex;
	Color color;

	//Getter , Setter
	public int getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadius() {
		return BALL_RADIUS;
	}

	public int getColorIndex() {
		return colorIndex;
	}
	
	//생성자
	public Ball(int x, int y, int xSpeed, int ySpeed, int colorIndex) {
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.colorIndex = colorIndex;
		this.color = InkBallColor.fromIndex(colorIndex).getColor();
	}
	
	//ball 이동
	public void move() {
		x += xSpeed;
		y += ySpeed;
	}

	//Line과의 거리
	public double getDistanceToPoint(LinePoint p) {
		int ballX = this.x;
		int ballY = this.y;

		int pointX = p.getX();
		int pointY = p.getY();

		return Math.sqrt(Math.pow(pointX - ballX, 2) + Math.pow(pointY - ballY, 2));
	}

	//Line에 닿으면 공 튀기기
	public void bounceOffPoint(LinePoint collisionPoint) {
		// 현재 공의 중심 좌표
		int ballX = this.x;
		int ballY = this.y;

		double normalX = collisionPoint.getX() - ballX;
		double normalY = collisionPoint.getY() - ballY;

		double magnitude = Math.sqrt(normalX * normalX + normalY * normalY);
		if (magnitude == 0) return;
		normalX /= magnitude;
		normalY /= magnitude;

		double dotProduct = xSpeed * normalX + ySpeed * normalY;

		double reflectedX = xSpeed - 2 * dotProduct * normalX;
		double reflectedY = ySpeed - 2 * dotProduct * normalY;

		double originalSpeedSum = Math.abs(xSpeed) + Math.abs(ySpeed);
		double reflectedSpeedSum = Math.abs(reflectedX) + Math.abs(reflectedY);

		double scale = originalSpeedSum / reflectedSpeedSum;

		xSpeed = (int) Math.round(reflectedX * scale);
		ySpeed = (int) Math.round(reflectedY * scale);
	}

	//ball 그리기
	public void paint(Graphics g, JPanel p) {
		Graphics2D g2d = (Graphics2D) g;

		// 타원 그리기
		Ellipse2D oval = new Ellipse2D.Double(
				x - BALL_RADIUS,
				y - BALL_RADIUS,
				2 * BALL_RADIUS,
				2 * BALL_RADIUS
		);

		// 클리핑 설정
		g2d.setClip(oval);

		// 이미지 로드
		Image ballImage = switch (colorIndex) {
			case 0 -> new ImageIcon(getClass().getResource("/res/gray_ball.png")).getImage();
			case 1 -> new ImageIcon(getClass().getResource("/res/orange_ball.png")).getImage();
			case 2 -> new ImageIcon(getClass().getResource("/res/blue_ball.png")).getImage();
			case 3 -> new ImageIcon(getClass().getResource("/res/green_ball.png")).getImage();
			case 4 -> new ImageIcon(getClass().getResource("/res/yellow_ball.png")).getImage();
			default -> null;
		};

		// 이미지 그리기 (oval 안에만 표시)
		g2d.drawImage(
				ballImage,
				x - BALL_RADIUS,
				y - BALL_RADIUS,
				2 * BALL_RADIUS,
				2 * BALL_RADIUS,
				p
		);

		// 클리핑 해제 (다른 그래픽 작업에 영향 없도록)
		g2d.setClip(null);
	}
}





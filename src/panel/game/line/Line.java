package panel.game.line;

import panel.game.ball.Ball;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//사용자가 그리는 선
public class Line {
	
	//하나의 Line을 구성하는 좌표 저장
	private List<LinePoint> line = new ArrayList<>();
	private List<LinePoint> drawingLine = new ArrayList<>();
	private final static int STROKE_SIZE = 8;
	private int interpolation = 20;

	//ball과 충돌시 작동
    public boolean collide(Ball ball) {
		for (LinePoint point : line) {
			if (ball.getDistanceToPoint(point) < ball.getRadius()) {
				System.out.println("Collision detected from -> ");
				System.out.println("Start : " + point.getX() + ", " + point.getY());
				System.out.println("End : " + point.getX() + ", " + point.getY());

				ball.bounceOffPoint(point); // 공의 반사 처리
				return true;
			}
		}
		return false;
    }

	//선을 그리기, 점과 점사이를 채우는 보간법 이용
	public void addLinePoint(int x, int y, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(x - STROKE_SIZE / 2, y - STROKE_SIZE / 2, STROKE_SIZE, STROKE_SIZE);

		if (!drawingLine.isEmpty()) {
			LinePoint prevPoint = drawingLine.get(drawingLine.size() - 1);

			// 두 점 사이에 선을 그리기
			g2d.drawLine(prevPoint.getX(), prevPoint.getY(), x, y);
			g2d.setStroke(new BasicStroke(STROKE_SIZE));

			// 50등분 지점 계산
			int x1 = prevPoint.getX();
			int y1 = prevPoint.getY();
			int x2 = x;
			int y2 = y;

			// 1/50, 2/50, ..., 49/50 지점 계산
			for (int i = 0; i < interpolation; i++) {
				int xDivide = (x1 * (interpolation - i) + x2 * i) / interpolation;
				int yDivide = (y1 * (interpolation - i) + y2 * i) / interpolation;

				// 50등분 점을 drawingLine에 추가
				drawingLine.add(new LinePoint(xDivide, yDivide));
			}
		}

		// 현재 점 추가
		drawingLine.add(new LinePoint(x, y));
	}



	public void drawEnd(){
		line = drawingLine;
		drawingLine = new ArrayList<>();
	}

	public void paintLine(Graphics2D g2d){
        for (LinePoint point : line) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(point.getX(), point.getY(), STROKE_SIZE, STROKE_SIZE);
        }
	}
}
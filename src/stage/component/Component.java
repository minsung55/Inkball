package stage.component;

import panel.game.ball.Ball;

import java.awt.*;

//다형성을 이용하기 위한 Component Interface
public interface Component{
    public final int cellSize = 40, offset = 0;
    public int collide(Ball ball);  //충돌시 작동하는 코드
    public void render(Graphics2D g2d); //panel에 그려지는 코드
}

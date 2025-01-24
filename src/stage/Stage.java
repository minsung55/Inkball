package stage;

import panel.game.ball.Ball;
import stage.component.*;
import stage.component.Component;
import stage.maps.GameMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Stage
public class Stage {
    
    private int[][] map;

    private GameMap gameMap;
    private List<List<Component>> renderedMap = new ArrayList<>();

    public List<Integer> getDispenserPos() {
        return dispenserPos;
    }

    private List<Integer> dispenserPos = new ArrayList<>();

    public Stage(GameMap map) {
        this.map = map.getMap();
        this.gameMap = map;

        for (int row = 0; row < GameMap.LEN; row++) {
            //다형성을 이용해 component로 받음
            List<Component> components = new ArrayList<>();
            for (int col = 0; col < GameMap.LEN; col++) {
                int value = this.map[row][col];
                Component component = switch (value) {
                    case 0 -> new Empty(row, col);
                    case 1 -> new Wall(row, col);
                    case 3 -> {
                        dispenserPos.add(col * Component.cellSize + Component.cellSize/2);
                        dispenserPos.add(row * Component.cellSize + Component.cellSize/2);
                        yield new Dispenser(row, col);
                    }
                    default -> new Hole(row, col, value % 10);
                };
                components.add(component);
            }
            renderedMap.add(components);
        }
    }

    //충돌 check
    public int checkCollide(List<Ball> balls) {
        int score = 0;
        
        for (List<Component> components : renderedMap) {
            for (Component component : components) {
                // Iterator를 사용하여 balls 리스트를 순회
                Iterator<Ball> iterator = balls.iterator();
                while (iterator.hasNext()) {
                    Ball ball = iterator.next();
                    //component의 collide 함수 호출 -> wall, dispenser, empty, hole의 collide함수가 호출됨
                    int isCollide = component.collide(ball);
                    score += isCollide;

                    // 충돌한 경우 해당 공을 리스트에서 제거
                    if (isCollide != 0) {
                        iterator.remove();
                    }
                }
            }
        }
        return score;
    }

    public void paintMap(Graphics2D g2d) {
        for (List<Component> components : renderedMap) {
            for (Component component : components) {
                //component의 render 함수 호출 -> wall, dispenser, empty, hole의 render() 호출됨
                component.render(g2d);
            }
        }
    }

    public List<Integer> getBallColors(){
        return gameMap.getBallColors();
    }

    public int getLeftTime() {
        return gameMap.getLeftTime();
    }

    public int getSpeed() {
        return gameMap.getSpeed();
    }
}

package panel.game;

import panel.info.InfoPanel;
import panel.game.ball.Ball;
import panel.game.line.Line;
import stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel{
    public static final int WIDTH = 695;
    public static final int HEIGHT = 715;

    private List<Ball> balls = new ArrayList<>();
    private int ballCount = 0;

    private int goalCount = 0;

    private Timer gameTimer;
    private Timer ballTimer;

    private int nowStage = 0;
    private List<Stage> stageList;
    private Stage stage;

    private List<Line> lines = new ArrayList<>();
    private InfoPanel infoPanel;

    //생성자
    public GamePanel(InfoPanel infoPanel) {
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.infoPanel = infoPanel;
        
        //Drag시 line이 그려짐
        MouseAdapter ma = new MouseAdapter() {
            Line line = null;
            @Override
            public void mousePressed(MouseEvent e) {
                line = new Line();
                line.addLinePoint(e.getX(), e.getY(), getGraphics());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                line.drawEnd();
                lines.add(line);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                line.addLinePoint(e.getX(), e.getY(), getGraphics());
            }
        }; addMouseListener(ma); addMouseMotionListener(ma);

        //16ms 마다 화면 출력
        gameTimer = new Timer(16, new ActionListener() { // 약 60FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                if(balls != null) {
                    int score = stage.checkCollide(balls);
                    infoPanel.setScore(infoPanel.getScore() + score);
                    if(score != 0) goalCount += 1;

                    for(Ball ball : balls){
                        ball.move();
                        for(int i = 0; i<lines.size(); i++){
                            if(lines.get(i).collide(ball)) lines.remove(i);
                        }
                    }
                }
                if(infoPanel.getLeftTime() == 0) {
                    setStageList(stageList);
                    infoPanel.setStageList(stageList);
                }

                infoPanel.setSlot(String.valueOf(balls.size()));

                if(goalCount == stage.getBallColors().size()) {
                    goNextStage();
                    infoPanel.goNextStage();
                    goalCount = 0;
                }
                repaint();
            }
        });
        
        //남은 공이 있다면 2초마다 남은 공 생성
        ballTimer = new Timer(2000, new ActionListener() { // 약 60FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ballCount >= stage.getBallColors().size()) return;

                Random random = new Random();
                int x = random.nextInt(stage.getSpeed() + 1);

                balls.add(
                        new Ball(
                                stage.getDispenserPos().get(0),
                                stage.getDispenserPos().get(1),
                                x,
                                stage.getSpeed() - x,
                                stage.getBallColors().get(ballCount)
                        )
                );
                ballCount += 1;
            }
        });
    }

    //line 새로 그리기
    public void resetCanvas(){
        Graphics g = getGraphics();

        g.clearRect(0,0,WIDTH,HEIGHT);
        g.dispose();

        lines = new ArrayList<>();
        gameTimer.start();
        ballTimer.start();

        revalidate();
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if(stage != null) stage.paintMap(g2d);

        for (Line line : lines) {
            line.paintLine(g2d);
        }

        if(balls != null){
            for(Ball ball : balls){
                ball.paint(g, this);
            }
        }
    }

    //다음 스테이지로 이동
    public void goNextStage(){
        if(nowStage < stageList.size()) this.stage = this.stageList.get(nowStage);
        nowStage += 1;
        if(nowStage == stageList.size())  infoPanel.showResult();
        this.ballCount = 0;
        balls = new ArrayList<>();

        resetCanvas();
    }

    //스테이지 목록 지정
    public void setStageList(List<Stage> stageList){
        nowStage = 0;
        this.stageList = stageList;
        goNextStage();
    }

    //현재 스테이지를 별도로 지정
    public void setStage(Stage stage){
        this.stage = stage;
        resetCanvas();
    }
}
package panel.info;

import stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//ball 개수 슬롯, 현재 점수, 제한 시간 표기
public class InfoPanel extends JPanel {
    public static int WIDTH = 680;
    public static int HEIGHT = 35;

    private JTextField slot = new JTextField("Ball",15);
    private JTextField scoreBoard = new JTextField("Score",15);
    private JTextField timeBoard = new JTextField("TimeLeft",15);

    private LeftTimer leftTimer = null;
    private int nowStage = 0;
    private List<Stage> stageList;

    private int score = 0;

    //생성자
    public InfoPanel() {
        //기본 설정
        this.setBounds(0,40,WIDTH,HEIGHT);
        setBackground(Color.gray);
        setLayout(null);
        
        scoreBoard.setEnabled(false);
        timeBoard.setEnabled(false);

        //배경색 지정
        slot.setBackground(Color.DARK_GRAY);
        scoreBoard.setBackground(Color.BLACK);
        timeBoard.setBackground(Color.RED);

        //글자 색깔 지정
        slot.setForeground(Color.WHITE);
        scoreBoard.setForeground(Color.WHITE);
        timeBoard.setForeground(Color.WHITE);

        //글자 가운데 정렬
        slot.setHorizontalAlignment(JTextField.CENTER);
        scoreBoard.setHorizontalAlignment(JTextField.CENTER);
        timeBoard.setHorizontalAlignment(JTextField.CENTER);

        //위치 지정
        slot.setBounds(10, 3, 120, 25);
        scoreBoard.setBounds(WIDTH - 180, 5, 60, 25);
        timeBoard.setBounds(WIDTH - 180 + 60 + 40, 5, 60, 25);

        //슬롯, 점수창, 제한시간 추가
        add(slot);
        add(scoreBoard);
        add(timeBoard);
    }

    //스테이지 목록 추가
    public void setStageList(List<Stage> stageList){;
        if(leftTimer != null) leftTimer.stop();
        this.stageList = stageList;
        nowStage = 0;
        score = 0;
        goNextStage();
        leftTimer = new LeftTimer(
                stageList.get(0).getLeftTime(),
                this::setTimeBoard,
                this::endGame
        );

        leftTimer.start();
    }

    //다음 스테이지로 이동
    public void goNextStage(){
        nowStage += 1;
        if(nowStage == stageList.size())  {
            scoreBoard.setText("0");
            timeBoard.setText("TimeLeft");
            leftTimer.stop();
            return;
        }
        Stage stage = this.stageList.get(nowStage);
        slot.setText(String.valueOf(stage.getBallColors().size()));
    }

    //현재 남은 시간 return
    public int getLeftTime(){
        if(leftTimer == null) return 999;
        return leftTimer.getLeftTime();
    }

    //점수판 갱신
    public void setScoreBoard(){
        scoreBoard.setText(String.valueOf(score));
    }

    //현재 점수 return
    public int getScore() {
        return score;
    }

    //점수 set, 점수판 갱신
    public void setScore(int score) {
        this.score = score;
        setScoreBoard();
    }

    //제한시간 끝날 시, 게임끝
    public void endGame(){
        scoreBoard.setText("---");
        timeBoard.setText("---");
    }

    //제한 시간 창 갱신
    public void setTimeBoard(String s){
        timeBoard.setText(s);
    }

    //ball 개수 slot 갱신
    public void setSlot(String s){
        slot.setText(String.valueOf(s));
    }

    //게임 끝날 시, 안내창 출력
    public void showResult(){
        JOptionPane.showMessageDialog(this, "모든 Stage 클리어!\n 최종점수 : " + this.score, "알림", JOptionPane.INFORMATION_MESSAGE);

    }

}
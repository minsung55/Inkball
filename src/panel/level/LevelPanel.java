package panel.level;

import panel.game.GamePanel;
import panel.info.InfoPanel;
import stage.Stage;
import stage.maps.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//level을 지정하는 panel
public class LevelPanel extends JPanel {
    public static int WIDTH = 680;
    public static int HEIGHT = 40;

    //생성자
    public LevelPanel(GamePanel gamePanel, InfoPanel infoPanel) {
        this.setBounds(0, 0, WIDTH, HEIGHT);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JRadioButton easy = new JRadioButton("초급");
        JRadioButton hard = new JRadioButton("고급");
        JButton eraseInk = new JButton("잉크 지우기");

        //listener 등록
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //초급용 스테이지 목록 제작
                List<Stage> stageList = new ArrayList<>();
                stageList.add(new Stage(new GameMapEasy1()));
                stageList.add(new Stage(new GameMapEasy2()));
                stageList.add(new Stage(new GameMapEasy3()));
                stageList.add(new Stage(new GameMapStart()));

                gamePanel.resetCanvas();

                gamePanel.setStageList(stageList);
                infoPanel.setStageList(stageList);
            }
        });

        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //고급용 스테이지 목록 제작
                List<Stage> stageList = new ArrayList<>();
                stageList.add(new Stage(new GameMapHard1()));
                stageList.add(new Stage(new GameMapHard2()));
                stageList.add(new Stage(new GameMapHard3()));
                stageList.add(new Stage(new GameMapStart()));

                gamePanel.resetCanvas();

                gamePanel.setStageList(stageList);
                infoPanel.setStageList(stageList);
            }
        });

        eraseInk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.resetCanvas();
            }
        });

        //RadioButton 배경색
        easy.setBackground(Color.LIGHT_GRAY);
        hard.setBackground(Color.LIGHT_GRAY);

        //버튼 그룹화
        ButtonGroup level = new ButtonGroup();
        level.add(easy);
        level.add(hard);

        add(easy);
        add(hard);
        add(eraseInk);
    }
}
import javax.swing.*;

import panel.game.GamePanel;
import panel.info.InfoPanel;
import panel.level.LevelPanel;
import stage.Stage;
import stage.maps.GameMapStart;

import java.awt.*;


public class GameFrame extends JFrame{
	public static int WIDTH = 695;
	// 프레임의 height는 전체 height의 합
	public static int HEIGHT = GamePanel.HEIGHT + LevelPanel.HEIGHT + InfoPanel.HEIGHT;

	public GameFrame() {
		//이름, close 시 작업 지정
		super("Inkball");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//frame의 layout 지정
		Container con = getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

		//Game정보를 표시하는 panel
		InfoPanel infoPanel = new InfoPanel();
		infoPanel.setPreferredSize(new Dimension(WIDTH, InfoPanel.HEIGHT));

		//Game화면을 표시하는 panel
		GamePanel gamePanel = new GamePanel(infoPanel);
		gamePanel.setPreferredSize(new Dimension(WIDTH, GamePanel.HEIGHT));

		//GameLevel을 지정하는 panel
		LevelPanel levelPanel = new LevelPanel(gamePanel, infoPanel);
		levelPanel.setPreferredSize(new Dimension(WIDTH, LevelPanel.HEIGHT));

		//panel 추가
		con.add(levelPanel);
		con.add(infoPanel);
		con.add(gamePanel);

		//frame 사이즈 지정
		setSize(WIDTH, HEIGHT);

		//기본 설정
		setVisible(true);
		setResizable(false);

		//기본 스테이지 지정
		Stage startStage = new Stage(new GameMapStart());
		gamePanel.setStage(startStage);
	}
}


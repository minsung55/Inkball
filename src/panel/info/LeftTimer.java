package panel.info;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

//제한 시간용 타이머
public class LeftTimer {
    private Timer timer = null;
    int leftTime = 0;

    public LeftTimer(int limitedTime, Consumer<String> setScoreBoard, Runnable endGame){
        leftTime = limitedTime;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftTime -= 1;
                if(leftTime >= 0) setScoreBoard.accept(String.valueOf(leftTime));
                else{ endGame.run(); timer.stop(); }
            }
        });
    }

    public void start(){
        timer.start();
    }
    public void stop(){
        timer.stop();
    }

    public int getLeftTime(){
        return leftTime;
    }
}


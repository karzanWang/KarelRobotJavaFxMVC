package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import view.Main;

import java.io.Serializable;
//计时器
public class CountingThread implements Serializable {
    private static final long serialVersionUID = 1L;

    private double elapsed;//显示的记录的时间
    private boolean isStop = false;

    // 记录程序开始时间
    private double programStart = System.currentTimeMillis();

    // 程序一开始就是暂停的
    private double pauseStart ;

    // 程序暂停的总时间
    private double pauseCount = 0;

    private double oldTimer = 0;

    public double getElapsed(){
        return elapsed;
    }
    public void setProgramStart(double d){programStart = d;}
    public void setPauseCount(double d){pauseCount = d;}

    public CountingThread() {
        programStart = System.currentTimeMillis();
    }

    public void run(Label timeLabel,Main app) {
        app.timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev ->
        {
            elapsed = System.currentTimeMillis() - programStart - pauseCount - oldTimer;//计算时间
            timeLabel.setText(format(elapsed));
        }));
        app.timeline.setCycleCount(Animation.INDEFINITE);
        app.timeline.play();
    }

    public void stop(Main app){
        if(!isStop) {
            pauseStart = System.currentTimeMillis();//记录暂停的时间
            app.timeline.stop();
            isStop = true;
        }
    }

    public void start(Main app){
        if(isStop) {
            pauseCount += (System.currentTimeMillis() - pauseStart);//计算暂停的时长
            app.timeline.play();
            isStop = false;
        }

    }



    // 将毫秒数格式化
    public String format(double elapsed) {
        int hour, minute, second;

        elapsed = elapsed / 1000;

        second = (int) (elapsed % 60);
        elapsed = elapsed / 60;

        minute = (int) (elapsed % 60);
        elapsed = elapsed / 60;

        hour = (int) (elapsed % 60);

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
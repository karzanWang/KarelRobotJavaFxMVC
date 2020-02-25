package view;

import controller.CountingThread;
import controller.Server;
import item.Item;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application implements Item {
    //为了便于交互，在这层未封装
    StartStage startStage;
    GameStage gameStage;
    EndStage endStage;
    public Timeline timeline;
    MediaPlayer mdBGM;
    boolean hasWin = false;
    int stageNum = 1;
    Server server = new Server(1);
    Alert help = new Alert(Alert.AlertType.INFORMATION, server.help());


    @Override
    public void start(Stage primaryStage) {
        //打开开始界面
        startStage = new StartStage(this);

    }

    //main函数
    public static void main(String[] args) {
        Application.launch(args);
    }


}

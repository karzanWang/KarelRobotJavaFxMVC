package view;

import controller.Server;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static item.Item.mdBGM1;

public class StartStage extends Stage {

    //变量和封装
    private int stageNum = 1;
    private int backgroundNum = 1;
    private String mode = "普通";

    private void setStageNum(String s) {
        switch (s) {
            case "STAGE1":
                stageNum = 1;
                break;
            case "STAGE2":
                stageNum = 2;
                break;
            case "STAGE3":
                stageNum = 3;
                break;
            default:
                stageNum = 1;
                break;
        }

    }
    private void setBackgroundNum(String s) {
        switch (s) {
            case "bakground1":
                backgroundNum = 0;
                break;
            case "bakground2":
                backgroundNum = 1;
                break;
            case "bakground3":
                backgroundNum = 2;
                break;
            default:
                backgroundNum = 0;
                break;
        }
    }

    public int getStageNum() {
        return stageNum;
    }
    public int getBackgroundNum(){return backgroundNum;}
    public String getMode(){return mode;}

    StartStage(Main app) {
        //===初始化音乐===
        app.mdBGM= null;
        app.mdBGM = mdBGM1;
        app.mdBGM.setCycleCount(10000);
        app.mdBGM.play();
        //===定义基础元素===
        ChoiceBox<String> stageChooser = new ChoiceBox<String>(FXCollections.observableArrayList("请选择关卡", "STAGE1", "STAGE2", "STAGE3"));
        ChoiceBox<String> backgroundChooser = new ChoiceBox<String>(FXCollections.observableArrayList("请选择背景", "bakground1", "bakground2", "bakground3"));
        ChoiceBox<String> modeChooser = new ChoiceBox<String>(FXCollections.observableArrayList("请选择模式", "普通", "挑战"));
        final Button startGameButton = new Button("开始游戏");

        //开启面板，添加开始的图像
        GridPane startGUIPane = new GridPane();
        Image startImage = new Image("file:resource/start.png");
        ImageView startImv = new ImageView(startImage);
        startGUIPane.add(startImv, 1, 1);

        //设定开始按钮
        startGameButton.setMinSize(10, 10);
        startGameButton.setPrefSize(100, 50);
        startGameButton.setMaxSize(300, 300);
        startGameButton.getStyleClass().add("my-button");
        startGUIPane.add(startGameButton, 2, 3);
        startGameButton.setOnMouseClicked(event ->{
            app.startStage.hide();
            //app.timeline.pause();
            app.gameStage = new GameStage(app);
        });

        //三个选择器的设置和添加
        stageChooser.getSelectionModel().selectFirst();
        backgroundChooser.getSelectionModel().selectFirst();
        modeChooser.getSelectionModel().selectFirst();

        stageChooser.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, number2) -> {
                    System.out.println(stageChooser.getItems().get((Integer) number2));
                    setStageNum(stageChooser.getItems().get((Integer) number2));
                    app.stageNum = this.stageNum;
                    app.server = new Server(stageNum);
                });
        startGUIPane.add(stageChooser, 3, 3);

        backgroundChooser.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, number2) -> {
                    System.out.println(backgroundChooser.getItems().get((Integer) number2));
                    setBackgroundNum(backgroundChooser.getItems().get((Integer) number2));
                });
        startGUIPane.add(backgroundChooser, 4, 3);

        modeChooser.getSelectionModel().selectedIndexProperty().addListener(
                (observableValue, number, number2) -> {
                    System.out.println(modeChooser.getItems().get((Integer) number2));
                    mode = modeChooser.getItems().get((Integer) number2);
                });
        startGUIPane.add(modeChooser, 4, 4);

        //开局文字
        Text startText = new Text(200, 200, "Welcome to Karel's World");
        startText.setScaleX(3);
        startText.setScaleY(3);
        startText.setFill(Color.BROWN);
        startGUIPane.add(startText, 2, 1);

        Scene scene = new Scene(startGUIPane, 940, 705);
        scene.getStylesheets().add("file:css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle("karel");
        this.show();
    }


}

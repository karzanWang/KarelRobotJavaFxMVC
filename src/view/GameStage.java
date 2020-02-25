package view;

import controller.CountingThread;
import controller.Server;
import item.Item;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage implements Item {
    //计时器存档的时间记录
    double elapsedSave;

    //***文本框***
    final Label codeArea_label = new Label("代码区域");
    final TextArea codeArea = new TextArea();
    final Label logArea_label = new Label("日志消息");
    final TextArea logArea = new TextArea();
    final Label errorArea_label = new Label("错误提示");
    final TextArea errorArea = new TextArea();
    final ImageView backgroundImage = new ImageView();

    GameStage(Main app) {
        //初始化音乐和server
        app.server = new Server(app.stageNum);
        app.mdBGM = null;
        app.mdBGM = mdBGM1;
        app.mdBGM.setCycleCount(10000);
        app.mdBGM.play();
        //*****************************************************
        //开启面板，设置背景
        AnchorPane mainGUIPane = new AnchorPane();
        setBackground(app.startStage.getBackgroundNum());
        mainGUIPane.getChildren().add(backgroundImage);
        //***按钮***
        final Button runCodeButton = new Button("运行代码");
        final Button quitStageButton = new Button("退出关卡");

        //计时器
        Label timeLabel = new Label("00:00:00");
        timeLabel.setLayoutX(500);
        mainGUIPane.getChildren().add(timeLabel);

        //关卡名称
        String stageName = null;
        switch (app.server.getStageNum()) {
            case 1:
                stageName = "Stage1";
                break;
            case 2:
                stageName = "Stage2";
                break;
            case 3:
                stageName = "Stage3";
                break;
        }
        final Label stageName_label = new Label(stageName);
        stageName_label.setLayoutX(600);
        mainGUIPane.getChildren().add(stageName_label);


        //===添加代码输入框===
        codeArea_label.setLayoutX(10);
        codeArea_label.setLayoutY(30);
        codeArea.setWrapText(true);
        codeArea.setLayoutX(10);
        codeArea.setLayoutY(50);
        codeArea.setPrefWidth(300);
        codeArea.setPrefHeight(450);
        mainGUIPane.getChildren().addAll(codeArea_label, codeArea);
        //===添加错误提示框===
        errorArea_label.setLayoutX(10);
        errorArea_label.setLayoutY(510);
        errorArea.setWrapText(true);
        errorArea.setEditable(false);
        errorArea.setLayoutX(10);
        errorArea.setLayoutY(530);
        errorArea.setPrefWidth(300);
        errorArea.setPrefHeight(60);
        mainGUIPane.getChildren().addAll(errorArea_label, errorArea);
        //===添加日志消息框===
        logArea_label.setLayoutX(320);
        logArea_label.setLayoutY(510);
        logArea.setWrapText(true);
        logArea.setEditable(false);
        logArea.setLayoutX(320);
        logArea.setLayoutY(530);
        logArea.setPrefWidth(480);
        logArea.setPrefHeight(110);
        mainGUIPane.getChildren().addAll(logArea_label, logArea);
        //运行和退出按钮
        runCodeButton.setLayoutX(50);
        runCodeButton.setLayoutY(610);
        runCodeButton.getStyleClass().add("my-button");
        quitStageButton.setLayoutX(150);
        quitStageButton.setLayoutY(610);
        quitStageButton.getStyleClass().add("my-button");
        mainGUIPane.getChildren().addAll(runCodeButton, quitStageButton);
        //********************************************************************************
        //翻译地图
        GridPane creatMapPane = new GridPane();
        updateMap(app, creatMapPane);
        creatMapPane.setLayoutX(400);
        creatMapPane.setLayoutY(150);
        mainGUIPane.getChildren().add(creatMapPane);


        MenuBar menuBar = new MyMenuBar(app);
        VBox vBox = new VBox(menuBar);
        mainGUIPane.getChildren().add(vBox);

        //退出的实现
        quitStageButton.setOnMouseClicked(event -> {
            app.startStage = new StartStage(app);
            //app.timeline.pause();
            app.gameStage.hide();
            app.server = new Server(app.startStage.getStageNum());
        });

        //运行代码功能的绑定
        runCodeButton.setOnMouseClicked(event -> {
            app.server.karelWorld.printMapWithCheck();
            app.server.interpreter.translator(codeArea.getText(), app.server, this);
            updateMap(app, creatMapPane);
            if (app.startStage.getMode().equals("普通")) {
                if (app.server.karelWorld.isWin()) {
                    app.hasWin = true;
                    this.getCodeArea().setEditable(false);
                    app.endStage = new EndStage(app);
                }
                if (app.server.karelWorld.isFail()) {
                    app.hasWin = false;
                    app.endStage = new EndStage(app);
                    this.getCodeArea().setEditable(false);
                }
            } else if (app.startStage.getMode().equals("挑战")) {
                if (app.server.karelWorld.isWin()) {
                    app.hasWin = true;
                    this.getCodeArea().setEditable(false);
                    app.endStage = new EndStage(app);
                } else {
                    app.hasWin = false;
                    app.endStage = new EndStage(app);
                    this.getCodeArea().setEditable(false);
                }
            }
        });
        //**************************************************************************************************************************

        //存档
        ((MyMenuBar) menuBar).getMuSave().setOnAction(event -> {
            app.server.saveGame(app.server);
            try {
                app.server.saveTxt(codeArea.getText(), "code");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                app.server.saveTxt(logArea.getText(), "log");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                app.server.saveTxt(errorArea.getText(), "error");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Saved");
        });

        //读档
        ((MyMenuBar) menuBar).getMuLoad().setOnAction(event -> {
            app.server.countingThread.stop(app);
            app.server = app.server.loadGame(app.server);
            elapsedSave = app.server.countingThread.getElapsed();
            app.server.countingThread.setPauseCount(0);
            app.server.countingThread.setProgramStart(System.currentTimeMillis() - elapsedSave);
            app.server.countingThread.run(timeLabel, app);
            try {
                codeArea.setText(app.server.loadTxt("code"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                logArea.setText(app.server.loadTxt("log"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                errorArea.setText(app.server.loadTxt("error"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Loaded");
            app.server.karelWorld.printMapWithCheck();
            updateMap(app, creatMapPane);

        });


        //重玩
        ((MyMenuBar) menuBar).getRePlay().setOnAction(event -> {
            app.gameStage.hide();
            app.gameStage = null;
            app.gameStage = new GameStage(app);
        });


        //选择背景
        ((MyMenuBar) menuBar).getBackground1().setOnAction(event -> {
            setBackground(0);
        });
        ((MyMenuBar) menuBar).getBackground2().setOnAction(event -> {
            setBackground(1);
        });
        ((MyMenuBar) menuBar).getBackground3().setOnAction(event -> {
            setBackground(2);
        });


        //选择关卡
        ((MyMenuBar) menuBar).getStage1().setOnAction(event -> {
            app.gameStage.hide();
            app.stageNum = 1;
            app.gameStage = new GameStage(app);
        });
        ((MyMenuBar) menuBar).getStage2().setOnAction(event -> {
            app.gameStage.hide();
            app.stageNum = 2;
            app.gameStage = new GameStage(app);
        });
        ((MyMenuBar) menuBar).getStage3().setOnAction(event -> {
            app.gameStage.hide();
            app.stageNum = 3;
            app.gameStage = new GameStage(app);
        });


        //暂停继续
        ((MyMenuBar) menuBar).getPause().setOnAction(event -> {
            app.mdBGM.pause();
            //app.timeline.pause();
            app.server.countingThread.stop(app);
            codeArea.setEditable(false);
        });
        ((MyMenuBar) menuBar).getContin().setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM.setCycleCount(100);
            app.mdBGM.play();
            //app.timeline.play();
            app.server.countingThread.start(app);
            codeArea.setEditable(true);
        });


        Scene scene = new Scene(mainGUIPane, 940, 705);
        scene.getStylesheets().add("file:css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle("karel");
        this.show();
        app.server.countingThread = null;
        app.server.countingThread = new CountingThread();
        app.server.countingThread.run(timeLabel, app);


    }


    private Image setKarekImage(String s) {
        switch (s) {
            case DIRECTIONDOWN:
                return karelImage[KARELlDOWN];
            case DIRECTIONLEFT:
                return karelImage[KARELLEFT];
            case DIRECTIONRIGHT:
                return karelImage[KARELRIGHT];
            case DIRECTIONUP:
                return karelImage[KARELUP];
            default:
                return null;
        }

    }//设置机器人的图像

    private void updateMap(Main app, GridPane creatMapPane) {
        creatMapPane.getChildren().clear();
        Image karelImage = setKarekImage(app.server.karelWorld.getKarel().getCurrentDirection());
        for (int i = 0; i < app.server.karelWorld.getMap().length; i++) {
            for (int j = 0; j < app.server.karelWorld.getMap()[0].length; j++) {
                Image img = null;
                if (app.server.karelWorld.getMap()[i][j].getSymbol().equals(ROCK.getSymbol())) {
                    img = optionImage[ROCKNUM];
                }
                if (app.server.karelWorld.getMap()[i][j].getSymbol().equals(WALL.getSymbol())) {
                    img = optionImage[WALLNUM];
                }
                if (app.server.karelWorld.getMap()[i][j].getSymbol().equals(GROUNG.getSymbol())) {
                    img = optionImage[GROUNDNUM];
                }
                if (app.server.karelWorld.getMap()[i][j].getSymbol().equals(FIXEDTRAP.getSymbol())) {
                    img = optionImage[FIXEDTRAPNUM];
                }
                if (app.server.karelWorld.getMap()[i][j].getSymbol().equals(TRAP.getSymbol())) {
                    img = optionImage[TRAPNUM];
                }
                ImageView imv = new ImageView();
                imv.setImage(img);
                creatMapPane.add(imv, j, i);
                ImageView karelImv = new ImageView();
                karelImv.setImage(karelImage);
                creatMapPane.add(karelImv, app.server.karelWorld.getKarel().getPlaceY(), app.server.karelWorld.getKarel().getPlaceX());
            }
        }
    }//刷新地图

    private void setBackground(int i) {
        backgroundImage.setImage(Item.backgroundImage[i]);
    }//设置背景

    public void setLogArea(String s) {
        logArea.appendText(s + "\n");
    }//设置记录区域

    public void setErrorArea(String s) {
        errorArea.appendText(s + "\n");
    }//设置报错区域

    public TextArea getCodeArea() {
        return codeArea;
    }//获得代码命令


}


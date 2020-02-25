package view;

import controller.Server;
import item.Item;
import javafx.scene.control.*;

public class MyMenuBar extends MenuBar implements Item {

    //设定按钮等和封装
    private Menu muFile = new Menu("File");
    private Menu muSet = new Menu("Setting");
    private Menu muHelp = new Menu("Help");
    private Menu muSave = new Menu("Save");
    private Menu muLoad = new Menu("Load");
    private Menu muBGM = new Menu("Music");
    private Menu muBGM1 = new Menu("Music1");
    private Menu muBGM2 = new Menu("Music2");
    private Menu muVolume = new Menu("Volume");
    private Menu muMessage = new Menu("message");
    private Menu muBack = new Menu("Quit");
    private Menu pause = new Menu("Pause");
    private Menu contin = new Menu("Continue");
    private Menu rePlay = new Menu("Replay");

    private Menu stages = new Menu("Stages");
    private Menu stage1 = new Menu("Stage1");
    private Menu stage2 = new Menu("Stage2");
    private Menu stage3 = new Menu("Stage3");

    private Menu background = new Menu("Background");
    private Menu background1 = new Menu("Background1");
    private Menu background2 = new Menu("Background2");
    private Menu background3 = new Menu("Background3");

    private Menu mute = new Menu("Mute");

    public Menu getMuLoad() {
        return muLoad;
    }

    public Menu getMuSave() {
        return muSave;
    }

    public Menu getPause() {
        return pause;
    }

    public Menu getContin() {
        return contin;
    }

    public Menu getRePlay() {
        return rePlay;
    }

    public Menu getBackground1() {
        return background1;
    }

    public Menu getBackground2() {
        return background2;
    }

    public Menu getBackground3() {
        return background3;
    }

    public Menu getStage1() {
        return stage1;
    }

    public Menu getStage2() {
        return stage2;
    }
    public Menu getStage3() {
        return stage3;
    }

    MyMenuBar(Main app) {
        super();
        //控制音量的实现
        Slider slider = new Slider();
        slider.setValue(50);
        app.mdBGM.volumeProperty().bind(slider.valueProperty().divide(100));
        //添加元素
        CustomMenuItem muVolume1 = new CustomMenuItem(slider);
        muVolume.getItems().add(muVolume1);
        muHelp.getItems().addAll(muMessage, muBack, pause, contin, rePlay);
        muSet.getItems().addAll(muBGM, muVolume, mute);
        this.getMenus().addAll(muFile, muSet, muHelp, stages, background);
        background.getItems().addAll(background1, background2, background3);
        stages.getItems().addAll(stage1, stage2, stage3);
        muBGM.getItems().addAll(muBGM1, muBGM2);
        muFile.getItems().addAll(muSave, muLoad);


        muBack.setOnAction(event -> {
            app.gameStage.hide();
            app.mdBGM.pause();
            app.startStage.show();
            app.timeline.play();
        });//退出

        //切换音乐
        muBGM1.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = null;
            app.mdBGM = mdBGM1;
            app.mdBGM.setCycleCount(10000);
            app.mdBGM.play();
            app.mdBGM.volumeProperty().bind(slider.valueProperty().divide(100));
        });
        muBGM2.setOnAction(event -> {
            app.mdBGM.pause();
            app.mdBGM = null;
            app.mdBGM = mdBGM2;
            app.mdBGM.setCycleCount(10000);
            app.mdBGM.play();
            app.mdBGM.volumeProperty().bind(slider.valueProperty().divide(100));
        });


        muMessage.setOnAction(event -> app.help.showAndWait());//弹窗

        //选择关卡
        stage1.setOnAction(event -> {
            app.server = new Server(1);
            app.gameStage.hide();
            app.gameStage = null;
            app.gameStage = new GameStage(app);
        });
        stage2.setOnAction(event -> {
            app.server = new Server(2);
            app.gameStage.hide();
            app.gameStage = null;
            app.gameStage = new GameStage(app);
        });
        stage3.setOnAction(event -> {
            app.server = new Server(3);
            app.gameStage.hide();
            app.gameStage = null;
            app.gameStage = new GameStage(app);
        });

        //静音
        mute.setOnAction(event -> {
            app.mdBGM.setVolume(0);
        });

    }

}

package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndStage extends Stage {
    private String endWords;

    EndStage(Main app) {
        //定义元素
        BorderPane endGamePane = new BorderPane();
        final Button replayGameButton = new Button("重玩关卡");
        final Button quitGameButton = new Button("退出关卡");
        replayGameButton.getStyleClass().add("my-button");
        quitGameButton.getStyleClass().add("my-button");

        //设置结束文本
        setEndWords(app.hasWin);
        Text endText = new Text(200, 200, endWords);
        endText.setScaleX(2);
        endText.setScaleY(2);
        endText.setFill(Color.BROWN);
        endGamePane.setCenter(endText);

       //设置按钮和功能
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(100));
        HBox.setHgrow(replayGameButton, Priority.ALWAYS);
        HBox.setHgrow(quitGameButton, Priority.ALWAYS);
        quitGameButton.setMaxWidth(Double.MAX_VALUE);
        replayGameButton.setMaxWidth(Double.MAX_VALUE);
        bottom.getChildren().addAll(replayGameButton, quitGameButton);

        quitGameButton.setOnMouseClicked(event -> {
            app.startStage = new StartStage(app);
            app.endStage.hide();
            //app.timeline.pause();
            app.gameStage.hide();
        });//退出

        replayGameButton.setOnMouseClicked(event -> {
            app.endStage.hide();
            app.gameStage.hide();
            app.gameStage = new GameStage(app);
            app.gameStage.show();
        });//重玩

        endGamePane.setBottom(bottom);

        Scene scene = new Scene(endGamePane, 500, 500);
        scene.getStylesheets().add("file:css/style.css");
        this.setScene(scene);
        this.setResizable(false);
        this.setTitle("karel");
        this.show();
    }

    //判断最终结果
    public void setEndWords(boolean hasWin) {
        if (hasWin) {
            endWords = "Congratulations, you have won the game.";
        } else {
            endWords = "Game over.";
        }
    }
}

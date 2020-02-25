package item;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.MapElement;

import java.io.File;

//存放全局资源的接口

public interface Item {
    //两首音乐
    MediaPlayer mdBGM1 = new MediaPlayer(new Media(new File("media/Music1.mp3").toURI().toString()));
    MediaPlayer mdBGM2 = new MediaPlayer(new Media(new File("media/Music2.mp3").toURI().toString()));
    //背景图片
    Image[] backgroundImage = {new Image("file:resource/background1.jpg"), new Image("file:resource/background2.jpg"), new Image("file:resource/background3.jpg")};
    //地图资源
    Image[] optionImage = {new Image("file:resource/ground.png"), new Image("file:resource/wall.png")
            , new Image("file:resource/trap.png"), new Image("file:resource/rock.png"), new Image("file:resource/fixedtrap.png")};
    int GROUNDNUM = 0;
    int WALLNUM = 1;
    int TRAPNUM = 2;
    int ROCKNUM = 3;
    int FIXEDTRAPNUM = 4;
    //机器人的图片
    Image[] karelImage = {new Image("file:resource/karel/standLeft.png"), new Image("file:resource/karel/standRight.png")
            , new Image("file:resource/karel/standUp.png"), new Image("file:resource/karel/standDown.png")};
    int KARELLEFT = 0;
    int KARELRIGHT = 1;
    int KARELUP = 2;
    int KARELlDOWN = 3;
    //基础的地图元素
    MapElement WALL = new MapElement("WALL", "■", false);
    MapElement ROCK = new MapElement("ROCK", "●", false);
    MapElement GROUNG = new MapElement("GROUND", "·", true);
    MapElement TRAP = new MapElement("TRAP", "⊙", false);
    MapElement FIXEDTRAP = new MapElement("FIXED_TRAP", "×", true);
    //机器人的四个方位
    String DIRECTIONUP = "▲";
    String DIRECTIONDOWN = "▼";
    String DIRECTIONLEFT = "◄";
    String DIRECTIONRIGHT = "►";
    //解释器所需要的常量
    int MOVE = 1;
    int MOVE_FOR_STEPS = 2;
    int TURNLEFT = 3;
    int FAIL = 4;
    int PICK_ROCK = 5;
    int PUT_ROCK = 6;
    int Q = 7;
    int NO_ROCK_PRESENT = 8;
    int NO_ROCK_IN_BAG = 9;
    int SHOW_INFORMATION = 10;
}

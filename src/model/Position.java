package model;

import item.Item;

import java.io.Serializable;

//karel的位置类 便于一些位置控制操作
public class Position extends MapElement implements Serializable, Item {
    //基本的变量
    private int placeX = 0;
    private int placeY = 0;
    private int dy = 0, dx = 0;

    //*************************************************************
    //===一些封装===
    public void setPlaceX(int X) {
        placeX = X;
    }

    public int getPlaceX() {
        return placeX;
    }

    public void setPlaceY(int Y) {
        placeY = Y;
    }

    public int getPlaceY() {
        return placeY;
    }

    public void setPlaceXPlaceY(int X, int Y) {
        setPlaceX(X);
        setPlaceY(Y);
    }

    /**
     * X Y 坐标的设置
     */
    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    /**
     * dy dx 方向 的设置
     */
    public void setCurrentDirection(String Direction) {
        setSymbol(Direction);
    }

    public String getCurrentDirection() {
        return getSymbol();
    }

    //*************************************************************
//根据机器人的方向获取下一个位置的运算
    public void Delta(String dir) {//dir=direction方向
        if (dir.equals(DIRECTIONDOWN)) {
            dx = 1;
            dy = 0;
        } else if (dir.equals(DIRECTIONUP)) {
            dx = -1;
            dy = 0;
        } else if (dir.equals(DIRECTIONLEFT)) {
            dx = 0;
            dy = -1;
        } else if (dir.equals(DIRECTIONRIGHT)) {
            dx = 0;
            dy = 1;
        }

    }


}

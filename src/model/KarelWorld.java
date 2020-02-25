package model;

import java.io.Serializable;

public class KarelWorld implements Serializable {
    //基本变量和封装
    private int rockNum = 0, rockX = 0, rockY = 0;
    private int trapNum = 0;
    private Map mapSaver = new Map();
    private MapElement[][] map;
    private KarelRobot karel = new KarelRobot(1,1,"");
    private boolean isWin, isFail;

    public KarelRobot getKarel() {
        return karel;
    }

    public MapElement[][] getMap() {
        return map;
    }

    /**
     * 整合map和karel
     */
    public int getRockNum() {
        return rockNum;
    }

    public void setRockNum(int i) {
        rockNum = i;
    }

    public void setTrapNum(int i) {
        trapNum = i;
    }

    public int getTrapNum() {
        return trapNum;
    }
    public int getRockX(){return rockX;}
    public int getRockY(){return rockY;}

    //石头位置的重制
    public void reSetRockXY() {
        rockX = 0;
        rockY = 0;
    }

    //构造方法和刷新
    public KarelWorld(int i) {
        updateKarelWorld(i);
    }

    public void updateKarelWorld(int i) {
        karel = karel.setKarel(i);
        map = mapSaver.mapClone(mapSaver.getMap(i));
    }
     //打印地图并检测
    public void printMapWithCheck() {
        trapNum = 0;
        rockNum = 0;
        rockX = 0;
        rockY = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == karel.getPlaceX() &&
                        j == karel.getPlaceY()) {
                    System.out.print(karel.getCurrentDirection() + " ");
                } else {
                    System.out.print(map[i][j].getSymbol() + " ");
                    if (map[i][j].getSymbol().equals("●")) {
                        rockNum = rockNum + 1;
                        if (rockX == 0 && rockY == 0) {
                            rockX = i;
                            rockY = j;
                        }
                    } else if (map[i][j].getSymbol().equals("⊙")) {
                        trapNum = trapNum + 1;
                    }
                }
            }
            System.out.print("\n");
        }

    }



    //是否胜利或失败
    public boolean isWin() {
        setIsWin();
        if (isWin)
            System.out.println("Congratulations, you have won the game.");
        return isWin;
    }
    private void setIsWin() {
        isWin = rockNum == 0 && trapNum == 0;
    }

    public void setFail(boolean b){isFail = b;}
    public boolean isFail(){
        if(isFail){
        System.out.println("Game Over.");
        }
        return isFail;}



}



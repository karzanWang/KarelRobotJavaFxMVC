package model;

import item.Item;

import java.io.Serializable;

public class KarelRobot extends Position implements Serializable,Item{

    //基本是变量和封装

    private boolean noRockInBag_T_or_F = true;
    private boolean noRockPresent_T_or_F = false;
    private int rockNumInBag = 0;

    public int getRockNumInBag() {
        return rockNumInBag;
    }

    public void setRockNumInBag(int i) {
        this.rockNumInBag = i;
    }

    public boolean getNoRockInBag_T_or_F() {
        return noRockInBag_T_or_F;
    }

    public void setNoRockInBag_T_or_F(boolean b) {
        noRockInBag_T_or_F = b;
    }

    public boolean getNoRockPresent_T_or_F() {
        return noRockPresent_T_or_F;
    }

    public void setNoRockPresent_T_or_F(boolean b) {
        noRockPresent_T_or_F = b;
    }

    public KarelRobot(int X, int Y, String dir) {
        setPlaceXPlaceY(X, Y);
        setCurrentDirection(dir);
        setName("Karel");
        rockNumInBag = 0;
        noRockInBag_T_or_F = true;
    }//申明方法

    public KarelRobot setKarel(int i) {
        switch (i) {
            case 1:
                return new KarelRobot(1, 0, "►");
            case 2:
                return new KarelRobot(2, 0, "►");
            case 3:
                return new KarelRobot(4, 0, "►");
            default:
                return null;
        }
    }


    //**************************



}

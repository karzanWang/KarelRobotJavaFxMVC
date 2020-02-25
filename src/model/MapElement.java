package model;

import java.io.Serializable;

/**
 * 地图的元素
 */
public class MapElement implements Serializable {
    //******************************************************
    //=== 基本变量===
    private String name;
    private String symbol;
    private boolean accesible;

    //*********************************************************
    //===封装的函数===
    public boolean isAccessible() {
        return accesible;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String s) {
        symbol = s;
    }


    public void setName(String s) {
        this.name = s;
    }
    //*******************************************************8

    //是否能放置石头
    public boolean putRockHere() {
        if (this.name.equals("TRAP")) {
            return true;
        } else {
            return false;
        }
    }

    //是否能捡起石头
    public boolean pickRockFromHere() {
        if (this.name.equals("ROCK")) {
            return true;
        } else {
            return false;
        }
    }

    //*******************************************************

    //声明方法
    public MapElement(String name, String symbol, boolean accesible) {
        this.name = name;
        this.symbol = symbol;
        this.accesible = accesible;
    }

    public MapElement() {
    }

}

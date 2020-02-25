package model;

import item.Item;

import java.io.Serializable;

public class Map extends MapElement implements Serializable, Item {

    //the element of map
    //三张基本地图
    private MapElement mapArr1[][] = {{GROUNG, GROUNG, GROUNG, GROUNG, GROUNG, GROUNG},
            {GROUNG, GROUNG, GROUNG, GROUNG, GROUNG, ROCK},
            {GROUNG, GROUNG, GROUNG, GROUNG, GROUNG, GROUNG}};


    private MapElement mapArr2[][] = {{WALL, GROUNG, GROUNG, GROUNG, GROUNG, GROUNG},
            {WALL, GROUNG, GROUNG, GROUNG, GROUNG, ROCK},
            {GROUNG, GROUNG, WALL, WALL, WALL, WALL}};//stage2的地图

    private MapElement mapArr3[][] = {{GROUNG, GROUNG, GROUNG, GROUNG, WALL, GROUNG, GROUNG, ROCK},
            {GROUNG, GROUNG, ROCK, GROUNG, WALL, GROUNG, GROUNG, GROUNG},
            {GROUNG, GROUNG, GROUNG, GROUNG, TRAP, GROUNG, GROUNG, GROUNG},
            {GROUNG, GROUNG, GROUNG, GROUNG, WALL, WALL, WALL, WALL},
            {GROUNG, GROUNG, GROUNG, GROUNG, WALL, WALL, WALL, WALL}};


    //复制地图
    public MapElement[][] mapClone(MapElement[][] resource) {
        MapElement[][] output = new MapElement[resource.length][resource[0].length];
        for (int i = 0; i < resource.length; i++)
            for (int j = 0; j < resource[0].length; j++) {
                output[i][j] = resource[i][j];
            }
        return output;
    }


    //复制地图
    public MapElement[][] getMap(int i) {
        switch (i) {
            case 1:
                return mapArr1;
            case 2:
                return mapArr2;
            case 3:
                return mapArr3;
            default:
                return null;
        }
    }

}

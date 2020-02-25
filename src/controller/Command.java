package controller;


import item.Item;
import model.KarelWorld;
import view.GameStage;

import java.io.Serializable;

//***指令集***
public class Command implements Serializable, Item {

    private KarelWorld karelWorld;

    public Command(KarelWorld k) {
        karelWorld = k;
    }

    private void move() {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        int nextX = karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx();
        int nextY = karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy();
        if (trapCheck()) {
            karelWorld.setFail(true);
            System.out.println("Game Over.");
            return;
        }
        if (nextY > karelWorld.getMap()[0].length - 1 || nextX > karelWorld.getMap().length - 1 || nextY < 0 || nextX < 0 || !karelWorld.getMap()[nextX][nextY].isAccessible()) {
            System.out.println("Error: you can't go there.");
        } else {
            karelWorld.getKarel().setPlaceXPlaceY(nextX, nextY);
        }

    }

    private void noRockPresent(GameStage g) {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        int nextX = karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx();
        int nextY = karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy();
        if (boundCheck()) {
            if (karelWorld.getMap()[nextX][nextY] == ROCK) {
                System.out.println("false");
                g.setLogArea("noRockPresent:false");
                karelWorld.getKarel().setNoRockPresent_T_or_F(false);
            } else {
                System.out.println("true");
                g.setLogArea("noRockPresent:true");
                karelWorld.getKarel().setNoRockPresent_T_or_F(true);
            }
        } else {
            System.out.println("true");
            g.setLogArea("noRockPresent:true");
            karelWorld.getKarel().setNoRockPresent_T_or_F(true);
        }

    }

    private void move(int i) {
        for (int k = 0; k < i; k++) {
            move();
        }
    }

    private void turnLeft() {
        switch (karelWorld.getKarel().getCurrentDirection()) {
            case DIRECTIONDOWN:
                karelWorld.getKarel().setCurrentDirection(DIRECTIONRIGHT);
                break;
            case DIRECTIONLEFT:
                karelWorld.getKarel().setCurrentDirection(DIRECTIONDOWN);
                break;
            case DIRECTIONUP:
                karelWorld.getKarel().setCurrentDirection(DIRECTIONLEFT);
                break;
            case DIRECTIONRIGHT:
                karelWorld.getKarel().setCurrentDirection(DIRECTIONUP);
                break;
        }
    }

    private void pickRock() {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        int nextX = karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx();
        int nextY = karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy();
        if (karelWorld.getMap()[nextX][nextY].pickRockFromHere()) {
            karelWorld.getKarel().setRockNumInBag(karelWorld.getKarel().getRockNumInBag() + 1);
            karelWorld.setRockNum(karelWorld.getRockNum() - 1);
            karelWorld.getMap()[nextX][nextY] = GROUNG;
            karelWorld.reSetRockXY();
            System.out.println("you have " + karelWorld.getKarel().getRockNumInBag() + " rock");
        } else {
            System.out.println("Error: you can't do that.");
        }

    }

    private void putRock() {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        int nextX = karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx();
        int nextY = karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy();
        if (karelWorld.getMap()[nextX][nextY].putRockHere()) {
            karelWorld.getMap()[nextX][nextY] = FIXEDTRAP;
            karelWorld.getKarel().setRockNumInBag(karelWorld.getKarel().getRockNumInBag() - 1);
            System.out.println("you have" + karelWorld.getKarel().getRockNumInBag() + "rock");
            System.out.println("you fix the trap !");
        } else {
            System.out.println("Error: you can't do that.");
        }

    }

    private void noRockInBag(GameStage g) {
        if (karelWorld.getKarel().getRockNumInBag() == 0) {
            System.out.println("true");
            g.setLogArea("noRockInBag:ture");
            karelWorld.getKarel().setNoRockInBag_T_or_F(true);
        } else {
            System.out.println("false");
            g.setLogArea("noRockInBag:false");
            karelWorld.getKarel().setNoRockInBag_T_or_F(false);
        }
    }

    private boolean boundCheck() {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        return karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy() <= karelWorld.getMap()[0].length - 1 &&
                karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx() <= karelWorld.getMap().length - 1 &&
                karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy() >= 0 &&
                karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx() >= 0;

    }//边界检测

    private boolean trapCheck() {
        karelWorld.getKarel().Delta(karelWorld.getKarel().getCurrentDirection());
        if (boundCheck()) {
            return karelWorld.getMap()[karelWorld.getKarel().getPlaceX() + karelWorld.getKarel().getDx()][karelWorld.getKarel().getPlaceY() + karelWorld.getKarel().getDy()] == TRAP;
        } else {
            return false;
        }


    }//陷阱检测

    //所有指令的封装，代号在item接口中
    public void action(int i, String choice, GameStage g) {
        switch (i) {
            case MOVE:
                move();
                break;
            case MOVE_FOR_STEPS:
                int k = Integer.parseInt(choice.substring(5, choice.length() - 1));
                System.out.println("move for" + i + "steps.");
                g.setLogArea("move for" + i + "steps.");
                move(k);
                break;
            case TURNLEFT:
                turnLeft();
                break;
            case PICK_ROCK:
                pickRock();
                break;
            case PUT_ROCK:
                putRock();
                break;
            case NO_ROCK_PRESENT:
                noRockPresent(g);
                break;
            case NO_ROCK_IN_BAG:
                noRockInBag(g);
                break;
            default:
                System.out.println("cant find this function,please enter again");

                break;
        }
    }//功能封装


}

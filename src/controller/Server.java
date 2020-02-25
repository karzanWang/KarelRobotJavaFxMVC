package controller;

import model.KarelWorld;
import view.GameStage;


import java.io.*;

public class Server implements Serializable {
    public CountingThread countingThread;
    private int stageNum = 1;
    public KarelWorld karelWorld;
    public Command command;
    public Interpreter interpreter = new Interpreter();
    //**************************************************************

    public Server(int i) {
        stageNum = i;
        karelWorld = new KarelWorld(stageNum);
        command = new Command(karelWorld);
        countingThread = new CountingThread();
    }

    public int getStageNum() {
        return stageNum;
    }


    //单个代码运行
    public void runCommand(String s, GameStage g) {
        command.action(interpreter.interpreter(s), s, g);
        karelWorld.printMapWithCheck();
        if (karelWorld.isWin()) {
            //System.out.println("Congratulations, you have won the game");
        }
        if (karelWorld.isFail()) {
            //System.out.println("Game Over.");
        }
    }

    //帮助信息
    public String help() {
        return "帮助栏。";
    }


    //游戏存档
    public void saveGame(Server server) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./save/" + "server" + ".save"));
            oos.writeObject(server);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //载入游戏
    public Server loadGame(Server server) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./save/" + "server" + ".save"));
            server = (Server) ois.readObject();

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return server;
    }


    //文本存档
    public void saveTxt(String s, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("./save/" + fileName + ".txt"));
        bw.write(s);
        bw.flush();
        bw.close();

    }

    //文本读取
    public String loadTxt(String fileName) throws IOException {
        BufferedReader bw = new BufferedReader(new FileReader("./save/" + fileName + ".txt"));
        String line;
        String record = "";
        while ((line = bw.readLine()) != null) {
            //System.out.print(line + "\n");
            record = record + line + "\n";
        }
        bw.close();
        return record;
    }
}

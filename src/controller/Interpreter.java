package controller;

import item.Item;
import model.KarelWorld;
import view.GameStage;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter implements Serializable, Item {
    private boolean codeError = true;


    public int interpreter(String command) {
        int rel = 0;
        rel = command.matches("move\\(\\)") ? MOVE : rel;
        rel = command.matches("move\\(\\d\\)") ? MOVE_FOR_STEPS : rel;
        rel = command.matches("turnLeft\\(\\)") ? TURNLEFT : rel;
        rel = command.matches("fail\\(\\)") ? FAIL : rel;
        rel = command.matches("pickRock\\(\\)") ? PICK_ROCK : rel;
        rel = command.matches("putRock\\(\\)") ? PUT_ROCK : rel;
        rel = command.matches("Q") ? Q : rel;
        rel = command.matches("noRockPresent\\(\\)") ? NO_ROCK_PRESENT : rel;
        rel = command.matches("noRockInBag\\(\\)") ? NO_ROCK_IN_BAG : rel;
        rel = command.matches("showInformation\\(\\)") ? SHOW_INFORMATION : rel;
        return rel;

    }//简单的解释器 每个命令的代号寄放

    private String compile(Pattern pattern, String s, Server server, GameStage g) {
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            s = s.substring(matcher.group().length());
            System.out.println(s);
            System.out.println(matcher.group().substring(0, matcher.group().length() - 1));
            server.karelWorld.printMapWithCheck();
            server.runCommand(matcher.group().substring(0, matcher.group().length() - 1), g);
            server.karelWorld.printMapWithCheck();
            g.setLogArea(matcher.group().substring(0, matcher.group().length() - 1) + " done.");
            codeError = false;
        }
        return s;
    }//部分指令的解释器和执行器

    private String interpreterX(String input, Server server, GameStage g) {
        codeError = true;
        Pattern move = Pattern.compile("^move\\(\\);");
        Pattern moveForSteps = Pattern.compile("^move\\(\\d\\);");
        Pattern turnLeft = Pattern.compile("^turnLeft\\(\\);");
        Pattern pickRock = Pattern.compile("^pickRock\\(\\);");
        Pattern noRockPresent = Pattern.compile("^noRockPresent\\(\\);");
        Pattern noRockInBag = Pattern.compile("^noRockInBag\\(\\);");
        Pattern putRock = Pattern.compile("^putRock\\(\\);");
        while (input.length() > 0) {
            input = compile(move, input, server, g);
            input = compile(moveForSteps, input, server, g);
            input = compile(turnLeft, input, server, g);
            input = compile(pickRock, input, server, g);
            input = compile(noRockInBag, input, server, g);
            input = compile(noRockPresent, input, server, g);
            input = compile(putRock, input, server, g);
            input = judgeIfElse(input, server, g);
            input = judgeWhile(input, server, g);
            input = judgeShowInformation(input, server, g);
            if (codeError) {
                g.setErrorArea("代码有错误 运行终止");
                return null;
            }
        }
        return input;
    }//主体代码的解释和执行

    private boolean judgeBooleanCommand(String s, KarelWorld k, Command c, GameStage g) {
        boolean flag;
        switch (interpreter(s)) {
            case NO_ROCK_PRESENT:
                c.action(NO_ROCK_PRESENT, s, g);
                flag = k.getKarel().getNoRockPresent_T_or_F();
                break;
            case NO_ROCK_IN_BAG:
                c.action(NO_ROCK_IN_BAG, s, g);
                flag = k.getKarel().getNoRockInBag_T_or_F();
                break;
            default:
                System.out.println("wrong command");
                flag = true;
        }
        g.setLogArea(s + ": " + flag);
        return flag;

    }//两个boolean型命令

    private String judgeIfElse(String input, Server server, GameStage g) {
        Pattern p = Pattern.compile("^if\\(.*?\\)\\{.*?}else\\{.*?}");
        Matcher m = p.matcher(input);
        while (m.find()) {
            codeError = false;
            boolean flag = false;
            Pattern p1 = Pattern.compile("(?<=^if).*?(?=\\)\\{)");
            Matcher m1 = p1.matcher(input);
            while (m1.find()) {
                System.out.println("if:" + m1.group().substring(1));
                flag = judgeBooleanCommand(m1.group().substring(1), server.karelWorld, server.command, g);
            }

            if (flag) {
                Pattern p2 = Pattern.compile("(?<=\\)\\)).*?(?=}else\\{)");
                Matcher m2 = p2.matcher(input);
                while (m2.find()) {
                    String a = m2.group().substring(1);
                    String[] commandsA = a.split(";");
                    System.out.println("if:" + a);
                    for (int i = 0; i < commandsA.length; i++) {
                        System.out.println(commandsA[i]);
                        for (int j = 0; j < commandsA.length; j++) {
                            System.out.println(commandsA[i]);
                            server.runCommand(commandsA[i], g);
                            server.karelWorld.printMapWithCheck();

                        }
                    }
                }
            } else {
                Pattern p3 = Pattern.compile("(?<=}else).*?(?=})");
                Matcher m3 = p3.matcher(input);
                while (m3.find()) {
                    String b = m3.group().substring(1);
                    String[] commandsB = b.split(";");
                    System.out.println("if:" + b);
                    for (int j = 0; j < commandsB.length; j++) {
                        System.out.println(commandsB[j]);
                        for (int i = 0; i < commandsB.length; i++) {
                            System.out.println(commandsB[i]);
                            server.runCommand(commandsB[i], g);
                            server.karelWorld.printMapWithCheck();

                        }
                    }
                }
            }
            input = input.substring(m.group().length());
        }
        return input;
    }//if解析

    private String judgeWhile(String input, Server server, GameStage g) {
        String command = null;
        Pattern p = Pattern.compile("^while\\(.*?\\)\\{.*?}");
        Matcher m = p.matcher(input);
        while (m.find()) {
            codeError = false;
            boolean flag = false;
            Pattern p1 = Pattern.compile("(?<=^while).*?(?=\\)\\{)");
            Matcher m1 = p1.matcher(input);
            while (m1.find()) {
                command = m1.group().substring(1);
                System.out.println("while:" + m1.group().substring(1));
                flag = judgeBooleanCommand(m1.group().substring(1), server.karelWorld, server.command, g);
                System.out.println(flag);
            }

            Pattern p2 = Pattern.compile("(?<=\\)\\)).*?(?=})");
            Matcher m2 = p2.matcher(input);
            while (m2.find()) {
                String a = m2.group().substring(1);
                String[] commandsA = a.split(";");
                System.out.println("while:" + a);
                while (judgeBooleanCommand(command, server.karelWorld, server.command, g)) {
                    for (int i = 0; i < commandsA.length; i++) {
                        System.out.println(commandsA[i]);
                        server.runCommand(commandsA[i], g);
                        server.karelWorld.printMapWithCheck();
                    }
                }
            }
            input = input.substring(m.group().length());
        }
        return input;
    }//while解析

    private String judgeShowInformation(String input, Server server, GameStage g) {
        Pattern showInformation = Pattern.compile("^showInformation\\(\\);");
        Matcher matcher = showInformation.matcher(input);
        while (matcher.find()) {
            codeError = false;
            server.karelWorld.printMapWithCheck();
            System.out.println("you have " + server.karelWorld.getKarel().getRockNumInBag() + " rock in your bag.");
            System.out.println("there is " + server.karelWorld.getRockNum() + " rock that need you to collect");
            int step = Math.abs(server.karelWorld.getKarel().getPlaceX() - server.karelWorld.getRockX()) + Math.abs(server.karelWorld.getKarel().getPlaceY() - server.karelWorld.getRockY());
            System.out.println("You are " + step + " steps away from the rock");

            g.setLogArea("you have " + server.karelWorld.getKarel().getRockNumInBag() + " rock in your bag.");
            g.setLogArea("there is " + server.karelWorld.getRockNum() + " rock that need you to collect");
            g.setLogArea("You are " + step + " steps away from the rock");
            input = input.substring(matcher.group().length());
        }
        return input;
    }//showinformation解析

    public void translator(String input, Server server, GameStage gameStage) {
        //去除空格和回车
        String inputCopy = input;
        inputCopy = inputCopy.replace(" ", "");
        inputCopy = inputCopy.replace("\n", "");
        String a = "";
        //正则表达式匹配main函数
        Pattern pattern = Pattern.compile("Main\\(\\)+:?\\s*\\{(.+?)}(?=(Main\\(\\)+:?)|$)");
        Matcher matcher = pattern.matcher(inputCopy);
        if (matcher.find()) {
            gameStage.setLogArea("Main(){} is identified .");
        } else {
            gameStage.setErrorArea("Cannot identify Main(){} .");
            return;
        }
        Matcher matcher2 = pattern.matcher(inputCopy);
        while (matcher2.find()) {
            a = matcher.group().substring(7, matcher.group().length() - 1);
            System.out.println("Main:" + a);
            interpreterX(a, server, gameStage);
        }


    }//用户代码的解析和执行


}

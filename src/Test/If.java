package Test;

import javafx.css.Match;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class If {
    public static void main(String[] args) {
        String s = judgeIfElse("if(noRockPresent()){move();turnLeft();}else{pickRock();move();}move();");
        System.out.println(s);
    }

    public static String judgeIfElse(String input) {
        Pattern p = Pattern.compile("^if\\(.*?\\)\\{.*?}else\\{.*?}");
        Matcher m = p.matcher(input);
        while (m.find()) {
            boolean flag = true;
            Pattern p1 = Pattern.compile("(?<=^if).*?(?=\\)\\{)");
            Matcher m1 = p1.matcher(input);
            while (m1.find()) {
                System.out.println("if:" + m1.group().substring(1));
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
                    }
                }
            }
            input = input.substring(m.group().length());
        }
        return input;
    }
}

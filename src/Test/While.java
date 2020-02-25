package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class While {

    public static void main(String[] args) {
        judgeWhile("while(noRockPresent()){move();turnLeft();}");
    }


    public static String judgeWhile(String input) {
        Pattern p = Pattern.compile("^while\\(.*?\\)\\{.*?}");
        Matcher m = p.matcher(input);
        while (m.find()) {
            boolean flag = false;
            Pattern p1 = Pattern.compile("(?<=^while).*?(?=\\)\\{)");
            Matcher m1 = p1.matcher(input);
            while (m1.find()) {
                System.out.println("if:" + m1.group().substring(1));
            }

            Pattern p2 = Pattern.compile("(?<=\\)\\)).*?(?=})");
            Matcher m2 = p2.matcher(input);
            while (m2.find()) {
                String a = m2.group().substring(1);
                String[] commandsA = a.split(";");
                System.out.println("if:" + a);
                for (int i = 0; i < commandsA.length; i++) {
                    System.out.println(commandsA[i]);
                }
            }
            input = input.substring(m.group().length());
        }
        return input;
    }

}

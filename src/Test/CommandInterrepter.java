package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandInterrepter {
    public static void main(String[] args) {
        String s = interpreterX("move();turnLeft();if(){}move()");
        System.out.println(s);
    }

    public static String interpreterX(String input) {

        Pattern move = Pattern.compile("^move\\(\\);");
        Pattern moveForSteps = Pattern.compile("^move\\(\\d\\);");
        Pattern turnLeft = Pattern.compile("^turnLeft\\(\\);");
        Pattern pickRock = Pattern.compile("^pickRock\\(\\);");
        Pattern noRockPresent = Pattern.compile("^noRockPresent\\(\\);");
        Pattern noRockInBag = Pattern.compile("^noRockInBag\\(\\);");
        Pattern showInformation = Pattern.compile("^showInformation\\(\\);");
        input = compile(move , input);
        input = compile(moveForSteps, input);
        input = compile(turnLeft , input);
        input = compile(pickRock , input);
        input = compile(noRockInBag , input);
        input = compile(noRockPresent , input);
        input = compile(showInformation , input);


        return input;
    }

    public static String compile(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            s = s.substring(matcher.group().length());
            System.out.println(s);
            System.out.println(matcher.group().substring(0,matcher.group().length() - 1));
        }
        return s;
    }
}

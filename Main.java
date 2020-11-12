package hr.tel.fer.dz1.htmlregex;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String var = null;
        try {
            var = new String(Files.readString(Paths.get(args[0])));
        } catch(Exception e) {
            System.out.println("Neispravan put do datoteke!");
            System.exit(1);
        }

        while(true) {

            String command = sc.nextLine();

            if(command.equals("ALL")) {
                System.out.println(var);
            } else if(command.matches("ALL <\\w+>")) {
                String regex = command.split(" ")[1];
                regex = regex.substring(1, regex.length() - 1);
                String text = var.replaceAll("(?<=<"+regex+")[^>]*", "");
                regexMatcher("(?<=<" + regex + ">)[\\S\\s]+?(?=<\\/" + regex + ">)", text);
            } else if(command.equals("ALL email")) {
                regexMatcher("\\w*@\\w+.\\w{2,6}(.\\w+)*", var);
            } else if(command.equals("ALL IP")) {
                regexMatcher("(\\d{1,3}\\.){3}\\d{1,3}", var);
            } else if(command.equals("ALL date")) {
                regexMatcher("\\b(\\d{1,2}[\\W]){1}(\\d{1,2}[\\W]){1}(\\d{4}){1}\\b", var);
            } else if(command.equals("ALL tel")) {
                regexMatcher("(\\d+[\\s-]){2,3}(\\d+)", var);
            } else if(command.matches("ONLY <\\w+> \\d+")) {
                String regex = command.split(" ")[1];
                Integer number = Integer.parseInt(command.split(" ")[2]);
                regex = regex.substring(1, regex.length() - 1);
                String text = var.replaceAll("(?<=<"+regex+")[^>]*", "");
                regexMatcher("(?<=<" + regex + ">)[\\S\\s]+?(?=<\\/" + regex + ">)", text, number);
            } else if(command.matches("ONLY email \\d+")) {
                Integer number = Integer.parseInt(command.split(" ")[2]);
                regexMatcher("\\w*@\\w+.\\w{2,6}(.\\w+)*", var, number);
            } else if(command.matches("ONLY IP \\d+")) {
                Integer number = Integer.parseInt(command.split(" ")[2]);
                regexMatcher("(\\d{1,3}\\.){3}\\d{1,3}", var, number);
            } else if(command.matches("ONLY date \\d+")) {
                Integer number = Integer.parseInt(command.split(" ")[2]);
                regexMatcher("\\b(\\d{1,2}[\\W]){1}(\\d{1,2}[\\W]){1}(\\d{4}){1}\\b", var, number);
            } else if(command.matches("ONLY tel \\d+")) {
                Integer number = Integer.parseInt(command.split(" ")[2]);
                regexMatcher("(\\d+[\\s-]){2,3}(\\d+)", var, number);
            } else if(command.equals("HELP")) {
                executeHelp();
            } else if(command.matches("REGEX ID \\d+")) {
                Integer number = Integer.parseInt(command.split(" ")[2]);
                executeRegexId(number);
            } else if(command.equals("EXIT")) {
                sc.close();
                System.exit(1);
            } else {
                System.out.println("Nije unesena ispravna naredba!");
            }
        }

    }


    public static void regexMatcher(String regex, String document) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(document);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    public static void regexMatcher(String regex, String document, int n) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(document);

        while (matcher.find() && n != 0) {
            System.out.println(matcher.group());
            n--;
        }
    }

    public static void executeHelp() {

        Map<Integer, String> help = new LinkedHashMap<Integer, String>();

        help.put(1, "ALL");
        help.put(2, "ALL <tag>");
        help.put(3, "ALL email");
        help.put(4, "ALL IP");
        help.put(5, "ALL date");
        help.put(6, "ALL tel");
        help.put(7, "ONLY <tag> broj");
        help.put(8, "ONLY email broj");
        help.put(9, "ONLY IP broj");
        help.put(10, "ONLY date broj");
        help.put(11, "ONLY tel broj");
        help.put(12, "HELP");
        help.put(13, "REGEX ID broj");
        help.put(14, "EXIT");

        for(Integer key : help.keySet()) {
            System.out.printf("%15d\t%s\n", key, help.get(key));
        }
    }

    public static void executeRegexId(int number) {
        if(number < 1 || number > 13) {
            System.out.println("Neispravan id!");
            return;
        }

        Map<Integer, String> help = new LinkedHashMap<Integer,String>();

        help.put(1, "No regex used");
        help.put(2, "(?<=<" + "tagBody" + ">)[\\S\\s]+?(?=<\\/" + "tagBody" + ">)");
        help.put(3, "\\w*@\\w+.\\w{2,6}(.\\w+)*");
        help.put(4, "(\\d{1,3}\\.){3}\\d{1,3}");
        help.put(5, "\\b(\\d{1,2}[\\W]){1}(\\d{1,2}[\\W]){1}(\\d{4}){1}\\b");
        help.put(6, "(\\d+[\\s-]){2,3}(\\d+)");
        help.put(7, "(?<=<" + "tagBody" + ">)[\\S\\s]+?(?=<\\/" + "tagBody" + ">)");
        help.put(8, "\\w*@\\w+.\\w{2,6}(.\\w+)*");
        help.put(9, "(\\d{1,3}\\.){3}\\d{1,3}");
        help.put(10, "\\b(\\d{1,2}[\\W]){1}(\\d{1,2}[\\W]){1}(\\d{4}){1}\\b");
        help.put(11, "(\\d+[\\s-]){2,3}(\\d+)");
        help.put(12, "No regex used");
        help.put(13, "No regex used");
        help.put(14, "No regex used");

        System.out.format("%15d\t%s\n", number, help.get(number));

    }
}
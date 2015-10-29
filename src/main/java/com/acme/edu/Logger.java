package com.acme.edu;

public class Logger {

    public static void log(int message) {
        print("primitive: " + message);
    }

    public static void log(byte message) {
        print("primitive: " + message);
    }

    public static void log(char c) {
        print("char: " + c);
    }

    public static void log(boolean b) {
        String str = "true";
        if (!b) {
            str = "false";
        }
        print("primitive: " + str);
    }

    public static void log(String str) {
        print("string: " + str);
    }

    public static void log(Object o) {
        print("reference: " + o.toString());
    }

    private static void print(String str) {
        System.out.println(str);
    }

    public static void main(String[] argv) {
        System.out.println("Test");
    }
}

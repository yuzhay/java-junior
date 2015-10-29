package com.acme.edu;

public class Logger {
    public static void log(int message) {
        System.out.println("primitive: " + message);
    }

    public static void log(byte message) {
        System.out.println("primitive: " + message);
    }

    public static void log(char c) {
        System.out.println("char: " + c);
    }

    public static void log(boolean b) {
        String str = "true";
        if (!b) {
            str = "false";
        }
        System.out.println("primitive: " + str);
    }

    public static void main(String[] argv) {
        System.out.println("Test");
    }
}

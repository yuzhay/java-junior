package com.acme.edu;

/**
 * Smart logger.
 */
public class Logger {

    //region fields
    public static final String LOG_PRIMITIVE = "primitive";
    public static final String LOG_CHAR = "char";
    public static final String LOG_STRING = "string";
    public static final String LOG_REFERENCE = "reference";
    public static final String LOG_PRIMITIVES_MATRIX = "primitives matrix";
    public static final String LOG_PRIMITIVES_MULTIMATRIX = "primitives multimatrix";

    /**
     * Integer is used to determine unset state
     */
    private static Integer sum = null;

    private static int strCounter = 1;
    private static String lastStr = "";

    //endregion

    private Logger() {
    }

    //region public methods

    /**
     * Print integer parameter to log
     *
     * @param message print parameter
     */
    public static void log(int message) {
        if (!lastStr.isEmpty()) {
            close();
        }

        if (sum == null) {
            sum = 0;
        }

        if (message == Integer.MAX_VALUE) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = Integer.MAX_VALUE;
        } else {
            if ((long) message + (long) sum > Integer.MAX_VALUE) {
                print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
                sum = message;
            } else {
                sum += message;
            }
        }
    }

    /**
     * Print byte parameter to log
     *
     * @param message print parameter
     */
    public static void log(byte message) {
        if (!lastStr.isEmpty()) {
            close();
        }

        if (sum == null) {
            sum = 0;
        }

        if (message == Byte.MAX_VALUE) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = (int) Byte.MAX_VALUE;
        } else {
            sum += message;
        }
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public static void log(char message) {
        print(String.format("%s: %s", Logger.LOG_CHAR, message));
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public static void log(boolean message) {
        print(String.format("%s: %s", Logger.LOG_PRIMITIVE, message));
    }

    /**
     * Print String parameter to log
     *
     * @param message print parameter
     */
    public static void log(String message) {
        if (sum != null) {
            close();
        }

        if (lastStr.equals(message)) {
            strCounter++;
        } else {
            strCounter = 1;
            if (!lastStr.isEmpty()) {
                print(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
        }


        lastStr = message;
    }

    /**
     * Print Object parameter to log
     *
     * @param message print parameter
     */
    public static void log(Object message) {
        print(String.format("%s: %s", Logger.LOG_REFERENCE, message));
    }

    /**
     * Close log in order to print result
     */
    public static void close() {
        if (sum != null) {
            print(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = null;
        }

        if (strCounter > 1) {
            print(String.format("%s: %s (x%d)", Logger.LOG_STRING, lastStr, strCounter));
            strCounter = 1;
            lastStr = "";
        } else if (strCounter == 1) {
            if (!lastStr.isEmpty()) {
                print(String.format("%s: %s", Logger.LOG_STRING, lastStr));
            }
            lastStr = "";
        }
    }

    /**
     * Print sum of int array to log
     *
     * @param messages print parameter
     */
    public static void log(int... messages) {
        int sum = 0;
        for (int i = 0; i < messages.length; i++) {
            sum += messages[i];
        }
        print(String.format("%s: %d", Logger.LOG_PRIMITIVE, sum));
    }


    /**
     * Print two dimensions int array (matrix) to log
     *
     * @param messages print parameter
     */
    public static void log(int[][] messages) {
        StringBuilder sb = new StringBuilder();
        sb.append('{').append(System.lineSeparator());
        for (int[] message : messages) {
            sb.append('{');
            for (int j = 0; j < message.length; j++) {
                sb.append(message[j]);
                if (j < message.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append('}').append(System.lineSeparator());
        }
        sb.append('}');
        print(String.format("%s: %s", Logger.LOG_PRIMITIVES_MATRIX, sb.toString()));
    }

    /**
     * Print 4 dimensions int array (matrix) to log
     *
     * @param messages print parameter
     */
    public static void log(int[][][][] messages) {
        StringBuilder buffer = new StringBuilder();
        String sep = System.lineSeparator();
        buffer.append('{').append(sep);
        for (int[][][] aMessages : messages) {
            buffer.append('{').append(sep);
            for (int[][] bMessages : aMessages) {
                buffer.append('{').append(sep);
                for (int[] cMessages : bMessages) {
                    buffer.append('{').append(sep);
                    int messageIndex = 0;
                    while (messageIndex < cMessages.length) {
                        buffer.append(cMessages[messageIndex]);
                        if (messageIndex < cMessages.length - 1) {
                            buffer.append(", ");
                        }
                        messageIndex++;
                    }
                    buffer.append(sep).append('}').append(sep);
                }
                buffer.append('}').append(sep);
            }
            buffer.append('}').append(sep);
        }
        buffer.append('}');
        print(String.format("%s: %s", Logger.LOG_PRIMITIVES_MULTIMATRIX, buffer.toString()));
    }

    /**
     * Print String array to log
     *
     * @param messages print parameter
     */
    public static void log(String... messages) {
        StringBuilder buffer = new StringBuilder();
        for (String message : messages) {
            buffer.append(message).append(System.lineSeparator());
        }
        print(String.format("%s: %s", Logger.LOG_STRING, buffer.toString()));
    }
    //endregion

    //region private methods


    private static void print(String str) {
        System.out.println(str);
    }
    //endregion
}

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
    private static LogState state = LogState.START;
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
        prepareValuesAndClosePrimitiveLogIfNeed();

        if (message == Integer.MAX_VALUE) {
            state = LogState.INT_PRINT_MAXVALUE;
            state.print(message);
            return;
        }

        if (isOverflow(message)) {
            state = LogState.INT_PRINT_OVERFLOW;
        } else {
            state = LogState.INT_PRINT_SUM;
        }

        state.print(message);
    }

    /**
     * Print byte parameter to log
     *
     * @param message print parameter
     */
    public static void log(byte message) {
        prepareValuesAndClosePrimitiveLogIfNeed();

        if (message == Byte.MAX_VALUE) {
            state = LogState.BYTE_PRINT_MAXVALUE;
        } else {
            state = LogState.INT_PRINT_SUM;
        }

        state.print(message);
    }

    /**
     * Print char parameter to log
     *
     * @param message print parameter
     */
    public static void log(char message) {
        formatPrint(String.format("%s: %s", Logger.LOG_CHAR, message));
    }

    /**
     * Print boolean parameter to log
     *
     * @param message print parameter
     */
    public static void log(boolean message) {
        formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVE, message));
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
                formatPrint(String.format("%s: %s", Logger.LOG_STRING, lastStr));
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
        formatPrint(String.format("%s: %s", Logger.LOG_REFERENCE, message));
    }

    /**
     * Close log in order to print result
     */
    public static void close() {
        if (sum != null) {
            formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
            sum = null;
        }

        if (strCounter > 1) {
            formatPrint(String.format("%s: %s (x%d)", Logger.LOG_STRING, lastStr, strCounter));
            strCounter = 1;
            lastStr = "";
        } else if (strCounter == 1) {
            if (!lastStr.isEmpty()) {
                formatPrint(String.format("%s: %s", Logger.LOG_STRING, lastStr));
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
        formatPrint(String.format("%s: %d", Logger.LOG_PRIMITIVE, sum));
    }


    /**
     * Print two dimensions int array (matrix) to log
     *
     * @param messages print parameter
     */
    public static void log(int[][] messages) {
        String result = log2d(messages);
        formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVES_MATRIX, result));
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
                String result = log2d(bMessages);
                buffer.append(result);
                buffer.append(sep).append('}').append(sep);
            }
            buffer.append('}').append(sep);
        }
        buffer.append('}');
        formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVES_MULTIMATRIX, buffer.toString()));
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
        formatPrint(String.format("%s: %s", Logger.LOG_STRING, buffer.toString()));
    }
    //endregion

    //region private methods
    private enum LogState {
        START,
        INT_PRINT_SUM, INT_PRINT_MAXVALUE, INT_PRINT_OVERFLOW,
        STRING_PRINT_COUNT, STRING_PRINT,
        CHAR_PRINT,
        BYTE_PRINT_MAXVALUE,
        CLOSE;

        public void print(int message) {
            switch (state) {
                case START: /*Do nothing*/
                    break;
                case INT_PRINT_SUM:
                    sum += message;
                    break;
                case INT_PRINT_MAXVALUE:
                    formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
                    sum = Integer.MAX_VALUE;
                    break;
                case STRING_PRINT_COUNT:
                    break;
                case STRING_PRINT:
                    break;
                case INT_PRINT_OVERFLOW:
                    formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
                    sum = message;
                    break;
                case BYTE_PRINT_MAXVALUE:
                    formatPrint(String.format("%s: %s", Logger.LOG_PRIMITIVE, sum));
                    sum = (int) Byte.MAX_VALUE;
                    break;
                /*case CHAR_PRINT:
                    formatPrint(String.format("%s: %s", Logger.LOG_CHAR, message));
                    break;*/
                case CLOSE:
                    break;
            }
        }
    }

    private static void prepareValuesAndClosePrimitiveLogIfNeed() {
        if (lastStr == null) {
            lastStr = "";
        }

        if (!lastStr.isEmpty()) {
            close();
        }

        if (sum == null) {
            sum = 0;
        }
    }

    private static String log2d(int[][] messages) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('{').append(System.lineSeparator());
        for (int[] aMessage : messages) {
            buffer.append('{');
            for (int j = 0; j < aMessage.length; j++) {
                buffer.append(aMessage[j]);
                if (j < aMessage.length - 1) {
                    buffer.append(", ");
                }
            }
            buffer.append('}').append(System.lineSeparator());
        }
        buffer.append('}');
        return buffer.toString();
    }

    private static void formatPrint(String str) {
        System.out.println(str);
    }

    private static boolean isOverflow(int message) {
        return message + (long) sum > Integer.MAX_VALUE;
    }

    //endregion
}

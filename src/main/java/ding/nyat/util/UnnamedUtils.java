package ding.nyat.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class UnnamedUtils {
    public static String convertExceptionStackTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

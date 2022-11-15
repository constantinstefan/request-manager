package fii.request.manager.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;

public class StringUtil {
    public static String toUpperCase(String string) {
        return (string != null)
                ? string.toUpperCase()
                : null;
    }
}

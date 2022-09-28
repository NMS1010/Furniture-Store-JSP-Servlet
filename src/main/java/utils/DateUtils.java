package utils;

import java.time.LocalDate;
public class DateUtils {
    public static java.sql.Date dateNow() {
        return java.sql.Date.valueOf(LocalDate.now());
    }
}

package flgfy.qrlogi.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    //yyyy-MM-dd HH:mm:ss
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 문자열로
    public static String nowAsString() {
        return LocalDateTime.now().format(FORMATTER);
    }

    //LocalDateTime을 문자열로 포맷
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    // 문자열을 LocalDateTime으로 파싱
    public static LocalDateTime parse(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

}

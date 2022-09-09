package ai.ecma.appeticketserver.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

    public static long compareTwoTimeStamps(java.sql.Timestamp firstTime, java.sql.Timestamp secondTime) {
        long milliseconds1 = firstTime.getTime();
        long milliseconds2 = secondTime.getTime();

        long diff = milliseconds2 - milliseconds1;

        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffHours;
    }
}

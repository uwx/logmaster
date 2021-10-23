package fallk.logmaster;

/**
 * Created by Maxine on 23/10/2021.
 *
 * @author Maxine
 * @since 23/10/2021
 */
public class LogmasterUtils {
    private static final float hoursInDay = 24f;
    private static final float minutesInHour = 60f;
    private static final float secondsInMinute = 60f;
    private static final float milisecondsInSecond = 1000f;

    static String FormatTime(long timeStamp) {
        int ms = (int) (timeStamp - HLogger.initialTime);

        int secs = (int) (ms / milisecondsInSecond);

        int mins = (int) (secs / secondsInMinute);
        if (mins == 0) return FormatSecs(secs, ms);

        int hours = (int) (mins / minutesInHour);
        if (hours == 0) return FormatMins(mins, secs);

        int days = (int) (hours / hoursInDay);
        // ReSharper disable once ConvertIfStatementToReturnStatement
        if (days == 0) return FormatHours(hours, mins, secs);

        // else
        return FormatDays(days, hours, mins, secs);
    }

    private static String FormatSecs(int secs, int ms) {
        char[] chars = new char[
                1 + // +
                2 + // Pad0(secs)
                1 + // .
                1 + // Digit(ms / 100)
                1 //   s
        ];

        chars[0] = '+';
        Pad0(secs, chars, 1, 2);
        chars[3] = '.';
        chars[4] = Digit(ms / 100);
        chars[5] = 's';

        return new String(chars);
    }

    private static String FormatMins(int mins, int secs) {
        char[] chars = new char[
                1 + // +
                2 + // Pad0(mins)
                1 + // :
                2 //   Pad0(secs)
        ];

        chars[0] = '+';
        Pad0(mins, chars, 1, 2);
        chars[3] = ':';
        Pad0(secs, chars, 4, 5);

        return new String(chars);
    }

    private static String FormatHours(int hours, int mins, int secs) {
        char[] chars = new char[
                1 + // +
                2 + // Pad0(hours)
                1 + // :
                2 + // Pad0(mins)
                1 + // :
                2 //   Pad0(secs)
        ];

        chars[0] = '+';
        Pad0(hours, chars, 1, 2);
        chars[3] = ':';
        Pad0(mins, chars, 4, 5);
        chars[6] = ':';
        Pad0(secs, chars, 7, 8);

        return new String(chars);
    }

    private static String FormatDays(int days, int hours, int mins, int secs) {
        String dayString = Integer.toString(days);
        int dayLen = dayString.length();

        char[] chars = new char[
                1 + // +
                dayLen + // dayString
                1 + // :
                2 + // Pad0(hours)
                1 + // :
                2 + // Pad0(mins)
                1 + // :
                2 //   Pad0(secs)
        ];

        chars[0] = '+';
        for (int i = 0; i < dayLen; i++) {
            chars[i + 1] = dayString.charAt(i);
        }
        chars[1 + dayLen] = ':';
        Pad0(hours, chars, 2 + dayLen, 3 + dayLen);
        chars[4 + dayLen] = ':';
        Pad0(mins, chars, 5 + dayLen, 6 + dayLen);
        chars[7 + dayLen] = ':';
        Pad0(secs, chars, 8 + dayLen, 9 + dayLen);

        return new String(chars);
    }

    private static void Pad0(int num, char[] outArr, int outIndex1, int outIndex2) {
        if (num < 10) {
            outArr[outIndex1] = '0';
            outArr[outIndex2] = (char) (num + 48);
        } else {
            outArr[outIndex1] = (char) (num / 10 + 48);
            outArr[outIndex2] = (char) (num % 10 + 48);
        }
    }

    // unused:
    //private static void Pad0n3(int i, out char i1, out char i2, out char i3)
    //{
    //    if (i < 10)
    //    {
    //        i1 = '0';
    //        i2 = '0';
    //        i3 = (char) (i + 48);
    //    }
    //    else if (i < 100)
    //    {
    //        i1 = '0';
    //        i2 = (char) (i / 10 + 48);
    //        i3 = (char) (i % 10 + 48);
    //    }
    //    else
    //    {
    //        i1 = (char) (i / 100 + 48);
    //        i2 = (char) (i / 10 % 10 + 48);
    //        i3 = (char) (i % 10 + 48);
    //    }
    //}

    // for 0-9
    private static char Digit(int i) {
        return (char) (i + 48);
    }

    public static String FormatNow() {
        return FormatTime(System.currentTimeMillis());
    }
}

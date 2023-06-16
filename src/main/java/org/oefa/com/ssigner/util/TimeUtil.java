package org.oefa.com.ssigner.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static void showTime(String msg){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now) + " --- " + msg);
    }
    public static void throwE() throws Exception {
        throw new Exception("fds");
    }
}

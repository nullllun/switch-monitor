package cn.albumenj.switchmonitor.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具
 * 用于返回过期时间
 *
 * @author Albumen
 */
public class DateUtil {
    public static Date beforeNow(Integer day){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-day);
        date=calendar.getTime();

        return date;
    }
}

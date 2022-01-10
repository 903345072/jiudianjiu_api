package com.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class Holiday {
    public static HashMap<Integer, List> map = new HashMap<Integer, List>() {
        {
            put(1, Arrays.asList(1,31));
            put(2, Arrays.asList(2,3,4,5,6));
            put(3, Arrays.asList(111,222));
            put(4, Arrays.asList(3,4,5));
            put(5, Arrays.asList(1,2,3,4));
            put(6, Arrays.asList(3,333));
            put(7, Arrays.asList(3,4,10,11,17,18,24,25,31));
            put(8, Arrays.asList(1,7,8,14,15,21,22,28,29));
            put(9, Arrays.asList(12,111));
            put(10, Arrays.asList(3,4,5,6,7));
            put(11, Arrays.asList(333,333));
            put(12, Arrays.asList(333,333));
        }
    };

    public static boolean is_trade_day(Calendar c){
        SimpleDateFormat sdf = new SimpleDateFormat("M-d");
        String tomorrow_day = sdf.format(c.getTime()); //7-1
        String[] split = tomorrow_day.split("-");
        Integer month_ = Integer.parseInt(split[0]);
        Integer day_ = Integer.parseInt(split[1]);
        List lists = Holiday.map.get(month_);
        if(!lists.contains(day_)){
            return true;
        }else{
            return false;
        }
    }

    public static  Date findEndTime(Integer limit_day){
        SimpleDateFormat formatter_  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR_OF_DAY,14);
        c1.set(Calendar.MINUTE,50);
        c1.set(Calendar.SECOND,0);
        int i1 = new Date().compareTo(c1.getTime());
        if(limit_day == 1 && i1 == -1){
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY,15);
            c2.set(Calendar.MINUTE,0);
            c2.set(Calendar.SECOND,0);
            return c2.getTime();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,1);
        int i = 0;
        boolean is_weekend;
        is_weekend = c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        while (true){
            if(is_weekend || !Holiday.is_trade_day(c)){ //如果不是交易日

            }else{
                i++;
            }
            if(i == limit_day){
                break;
            }
            c.add(Calendar.DATE,1);
        }

        c.set(Calendar.HOUR_OF_DAY,15);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
       return c.getTime();
    }
}

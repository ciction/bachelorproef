package org.optaplanner.examples.curriculumcourse.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 * Created by Christophe on 12/6/2016.
 */
public class WeekConfiguration extends AbstractPersistable{

    public static final int MONDAY      = 1;
    public static final int TUESDAY     = 2;
    public static final int WEDNESDAY   = 3;
    public static final int THURSDAY    = 4;
    public static final int FRIDAY      = 5;
    public static final int SATURDAY    = 6;
    public static final int SUNDAY      = 7;


//    public static enum Daynames{
//        MONDAY, TUESDAY, WEDNESDAY,THURSDAY, FRIDAY, SATURDAY, SUNDAY
//    }

    //{"invalid day", "Mo", "Tu", "We", "Th", "Fr", "Sat", "Sun"};
    public static  int[] WEEKDAYPeriods = {-1,0,0,0,0,0,0,0};

    public static void setWEEKDAYPeriods(int dayName, int period ){
        WEEKDAYPeriods[dayName] = period;
    }

}

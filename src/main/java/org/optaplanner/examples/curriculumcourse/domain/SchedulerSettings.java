package org.optaplanner.examples.curriculumcourse.domain;

import java.util.Date;

/**
 * Created by Christophe on 2/27/2017.
 */
public final class SchedulerSettings {
    public enum Language {
        Dutch,
        English
    };
    public static Language language = Language.Dutch;


    public static int day = 1;
    public static int month = 3;
    public static int year = 2017;
    private static final Date today = new Date();

//    public static final Date startDate = today;
    public static final Date startDate = new Date(year-1900,month-1,day);

    public static final int startDay = (startDate.getDay() == 0) ? 7: startDate.getDay();


}

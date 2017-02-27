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


    public static int day = 2;
    public static int month = 3;
    public static int year = 2017;
    public static final Date startDate = new Date(year-1900,month-1,day);

}

/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.curriculumcourse.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import sun.security.ssl.Debug;

@XStreamAlias("Day")
public class Day extends AbstractPersistable {


    private static  String[] WEEKDAYS = {"Mo", "Tu", "We", "Th", "Fr", "Sat", "Sun"};
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static int DayInMilliSeconds = 1000 * 60 * 60 * 24;
    private static final Date today = new Date();
    private static boolean isInitialized = false;


    private static void init(){
        if(SchedulerSettings.language.equals(SchedulerSettings.Language.Dutch)){
            WEEKDAYS = new String[]{"Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"};
        }
        List weekdaysList = new ArrayList();
        weekdaysList =  Arrays.asList(WEEKDAYS);
        Collections.rotate(weekdaysList, - (SchedulerSettings.startDay -1));
        isInitialized = true;
    }

    private int dayIndex;
    private Date date;
    private String dateString;

    private List<Period> periodList;

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
        this.date  = new Date(SchedulerSettings.startDate.getTime() + (DayInMilliSeconds * dayIndex));
        this.dateString = dateFormat.format(this.date);
        if(!isInitialized){
            init();
        }
    }

    public List<Period> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<Period> periodList) {
        this.periodList = periodList;
    }

    public String getLabel() {
        String weekday = WEEKDAYS[dayIndex % WEEKDAYS.length];
        weekday = weekday + " " + dateString;
//        if (dayIndex > WEEKDAYS.length) {
//            return "Day " + dayIndex;
//        }
        return weekday;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return Integer.toString(dayIndex);
    }

}

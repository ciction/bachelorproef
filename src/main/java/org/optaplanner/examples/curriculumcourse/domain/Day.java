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

@XStreamAlias("Day")
public class Day extends AbstractPersistable {


    private static  String[] WEEKDAYS = {"Mo", "Tu", "We", "Th", "Fr", "Sat", "Sun"};
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    //ehb DayInMilliSeconds van int naar long (overflow --> teveel dagen)
    private static long DayInMilliSeconds = 1000 * 60 * 60 * 24;
    private static final Date today = new Date();
    private static boolean isInitialized = false;

    private int middayPauzeSlot1 = 4;
    private int middayPauzeSlot2 = 5;



    private static void init(){
        if(SchedulerSettings.language.equals(SchedulerSettings.Language.Dutch)){
            WEEKDAYS = new String[]{"Ma", "Di", "Wo", "Do", "Vr", "Za", "Zo"};
        }
        List weekdaysList = new ArrayList();
        weekdaysList =  Arrays.asList(WEEKDAYS);
        //dagen in de array juist zetten (offset rotation) zodat de eerste dag in da array de eerste dag van de week is
        Collections.rotate(weekdaysList, - (SchedulerSettings.startDay -1));
        isInitialized = true;
    }

    private int dayIndex;
    private Date date;
    private boolean weekend = false;
    private String dateString;

    private List<Period> periodList;

    public int getDayIndex() {
        return dayIndex;
    }

      //EHB
    // Het aanmaken van een dat index maakt ook een date aan
    // de datum wordt berekend op basis van de startdatum + de offset in de day index
    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
        this.date  = new Date(SchedulerSettings.startDate.getTime() + (DayInMilliSeconds * dayIndex));
        this.dateString = dateFormat.format(this.date);
        if(!isInitialized){
            init();
        }

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        if (calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY  ||
                calDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ){
            setWeekend(true);
        }else {
            setWeekend(false);
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

    public int getMiddayPauzeSlot1() {
        return middayPauzeSlot1;
    }

    public void setMiddayPauzeSlot1(int middayPauzeSlot1) {
        this.middayPauzeSlot1 = middayPauzeSlot1;
    }

    public int getMiddayPauzeSlot2() {
        return middayPauzeSlot2;
    }

    public void setMiddayPauzeSlot2(int middayPauzeSlot2) {
        this.middayPauzeSlot2 = middayPauzeSlot2;
    }

    @Override
    public String toString() {
        return Integer.toString(dayIndex);
    }

    public boolean getWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }
}

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

package org.optaplanner.examples.curriculumcourse.persistence;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.persistence.AbstractTxtSolutionImporter;
import org.optaplanner.examples.curriculumcourse.domain.*;

import static org.optaplanner.examples.curriculumcourse.domain.WeekConfiguration.*;

public class CurriculumCourseImporter extends AbstractTxtSolutionImporter {

    private static final String INPUT_FILE_SUFFIX = "ctt";
    private static final String SPLIT_REGEX = "[\\ \\t]+";

    public static void main(String[] args) {
        new CurriculumCourseImporter().convertAll();
    }

    public CurriculumCourseImporter() {
        super(new CurriculumCourseDao());
    }

    @Override
    public String getInputFileSuffix() {
        return INPUT_FILE_SUFFIX;
    }

    public TxtInputBuilder createTxtInputBuilder() {
        return new CurriculumCourseInputBuilder();
    }

    public static class CurriculumCourseInputBuilder extends TxtInputBuilder {


        //int wednessDayTimeSlots = 4;
        public  void setWeekConfiguration(){
            setWEEKDAYPeriods(MONDAY,8);
            setWEEKDAYPeriods(TUESDAY,8);
            setWEEKDAYPeriods(WEDNESDAY,8);
            setWEEKDAYPeriods(THURSDAY,8);
            setWEEKDAYPeriods(FRIDAY,8);
            setWEEKDAYPeriods(SATURDAY,0);
            setWEEKDAYPeriods(SUNDAY,0);
        }




        public Solution readSolution() throws IOException {
            CourseSchedule schedule = new CourseSchedule();
            schedule.setId(0L);

            setWeekConfiguration();


            // Name: ToyExample
            schedule.setName(readStringValue("Name:"));
            // Courses: 4
            int courseListSize = readIntegerValue("Courses:");
            // Rooms: 2
            int roomListSize = readIntegerValue("Rooms:");
            // Days: 5
            int dayListSize = readIntegerValue("Days:");
            // Periods_per_day: 4
            int timeslotListSize = readIntegerValue("Periods_per_day:");
            // Curricula: 2
            int curriculumListSize = readIntegerValue("Curricula:");
            // Constraints: 8
            int unavailablePeriodPenaltyListSize = readIntegerValue("Constraints:");

            Map<String, Course> courseMap = readCourseListAndTeacherList( schedule, courseListSize);

            readRoomList( schedule, roomListSize);

            Map<List<Integer>, Period> periodMap = createPeriodListAndDayListAndTimeslotList(
                                                    schedule, dayListSize, timeslotListSize);

            readCurriculumList( schedule, courseMap, curriculumListSize);

            readUnavailablePeriodPenaltyList( schedule, courseMap, periodMap, unavailablePeriodPenaltyListSize);

            readEmptyLine();

            readConstantLine("END\\.");
            createLectureList(schedule);

            int possibleForOneLectureSize = schedule.getPeriodList().size() * schedule.getRoomList().size();
            BigInteger possibleSolutionSize = BigInteger.valueOf(possibleForOneLectureSize).pow(
                    schedule.getLectureList().size());
            logger.info("CourseSchedule {} has {} teachers, {} curricula, {} courses, {} lectures," +
                    " {} periods, {} rooms and {} unavailable period constraints with a search space of {}.",
                    getInputId(),
                    schedule.getTeacherList().size(),
                    schedule.getCurriculumList().size(),
                    schedule.getCourseList().size(),
                    schedule.getLectureList().size(),
                    schedule.getPeriodList().size(),
                    schedule.getRoomList().size(),
                    schedule.getUnavailablePeriodPenaltyList().size(),
                    getFlooredPossibleSolutionSize(possibleSolutionSize));
            return schedule;
        }










        //comments over ctt file toegevoegd in deze functie
//        private Map<String, Course> readWeekTimeSlots(
//                CourseSchedule schedule, int courseListSize) throws IOException {
//
////            Map<String, Course> courseMap = new HashMap<String, Course>(courseListSize);
////            Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
////            List<Course> courseList = new ArrayList<Course>(courseListSize);
////            readEmptyLine();
//            readConstantLine("WEEKPERIODS:");
//            for (int i = 0; i < courseListSize; i++) {
//                Course course = new Course();
//                course.setId((long) i);
//                // Courses: <CourseID> <Teacher> <# Lectures> <MinWorkingDays> <# Students>
//                String line = bufferedReader.readLine();
//                String[] lineTokens = splitBySpacesOrTabs(line, 6);                     // 6 parameters per lijn, zelf een toegevoegd (uren dat een les duurt )
//                course.setCode(lineTokens[0]);                                          //naam van het vak
//                course.setTeacher(findOrCreateTeacher(teacherMap, lineTokens[1]));      //naam van de prof
//                course.setLectureSize(Integer.parseInt(lineTokens[2]));                 //lecuture size ? --> hoeveel keer het voorkomt ?
//                course.setMinWorkingDaySize(Integer.parseInt(lineTokens[3]));           //MinWorkingDaySize --> // Lectures of the same course should be spread out into a minimum number of days.
//                course.setCurriculumList(new ArrayList<Curriculum>());
//                course.setStudentSize(Integer.parseInt(lineTokens[4]));                 //setStudentSize aantal studenten voor dit vak
//
//                course.setLectureTime(Integer.parseInt(lineTokens[5]));
//
//                courseList.add(course);
//                courseMap.put(course.getCode(), course);
//            }
//            schedule.setCourseList(courseList);
//            List<Teacher> teacherList = new ArrayList<Teacher>(teacherMap.values());
//            schedule.setTeacherList(teacherList);
//            return courseMap;
//
//        }










        //comments over ctt file toegevoegd in deze functie
        private Map<String, Course> readCourseListAndTeacherList(
                CourseSchedule schedule, int courseListSize) throws IOException {


            Map<String, Course> courseMap = new HashMap<String, Course>(courseListSize);
            Map<String, Teacher> teacherMap = new HashMap<String, Teacher>();
            List<Course> courseList = new ArrayList<Course>(courseListSize);

            readEmptyLine();
            readConstantLine("COURSES:");
            for (int i = 0; i < courseListSize; i++) {                                   //Courses: 3 --> 3 times forloop
                Course course = new Course();
                course.setId((long) i);
                // Courses: <CourseID> <Teacher> <# Lectures> <MinWorkingDays> <# Students>
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line, 5);                     // 6 parameters per lijn, zelf een toegevoegd (uren dat een les duurt )
                course.setCode(lineTokens[0]);                                          //naam van het vak
                course.setTeacher(findOrCreateTeacher(teacherMap, lineTokens[1]));      //naam van de prof
                course.setLectureSize(Integer.parseInt(lineTokens[2]));                 //lecuture size ? --> hoeveel keer het voorkomt ?
                course.setMinWorkingDaySize(Integer.parseInt(lineTokens[3]));           //MinWorkingDaySize --> // Lectures of the same course should be spread out into a minimum number of days.
                course.setCurriculumList(new ArrayList<Curriculum>());
                course.setStudentSize(Integer.parseInt(lineTokens[4]));                 //setStudentSize aantal studenten voor dit vak

//                course.setLectureTime(Integer.parseInt(lineTokens[5]));

                courseList.add(course);
                courseMap.put(course.getCode(), course);
            }
            schedule.setCourseList(courseList);
            List<Teacher> teacherList = new ArrayList<Teacher>(teacherMap.values());
            schedule.setTeacherList(teacherList);
            return courseMap;

        }

        private Teacher findOrCreateTeacher(Map<String, Teacher> teacherMap, String code) {
            Teacher teacher = teacherMap.get(code);
            if (teacher == null) {
                teacher = new Teacher();
                int id = teacherMap.size();
                teacher.setId((long) id);
                teacher.setCode(code);
                teacherMap.put(code, teacher);
            }
            return teacher;
        }

        private void readRoomList(CourseSchedule schedule, int roomListSize)
                throws IOException {
            readEmptyLine();
            readConstantLine("ROOMS:");
            List<Room> roomList = new ArrayList<Room>(roomListSize);
            for (int i = 0; i < roomListSize; i++) {
                Room room = new Room();
                room.setId((long) i);
                // Rooms: <RoomID> <Capacity>
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line, 2);
                room.setCode(lineTokens[0]);
                room.setCapacity(Integer.parseInt(lineTokens[1]));
                roomList.add(room);
            }
            schedule.setRoomList(roomList);
        }


        private int getAmountOfWednesDays(int dayListSize){

            //antal volledige weken --> dus minstens zoveel woensdagen
            int weeks  =   (dayListSize + 1) / 7;
            int wednesDays_Count = 0;
            // dag 0 is maandag dus dag 2 is woensdag dus bij een onvolledige week vanaf 2 dagen komt er een woensdag bij
            wednesDays_Count = ((dayListSize  % 7 ) >= 2) ? weeks + 1 : weeks ;

            return wednesDays_Count;
        }
        private int getWednessdayTimeDifference(int timeslotListSize){
            //if a normal day is 9 hours --> wednesday is 5 hours shorter  (timeslot - wednessDayTimeSlots)
            return (timeslotListSize - WEEKDAYPeriods[WEDNESDAY]);
        }


        private int GetDayNrOfTheWeek(int dayNr/*, int CheckWeekdayNr*/){
            //check if dayNr is this day of the week
            //monday == 0 - 7 - 14
            //tuesday == 1 -8 - 15
            //... etc
//            return (dayNr > 0 && ((dayNr % ( 7 + CheckWeekdayNr)) == 0 || dayNr == CheckWeekdayNr) );

            if(dayNr < 7) return (dayNr + 1);
            else return ((dayNr % 7) +1);
        }

        private void createPeriodListForDay(Day newDay, int dayNumber, int dayName,  List<Period> newPeriodList, List<Timeslot> newTimeslotList, Map<List<Integer>, Period> newPeriodMap){

            for (int j = 0; j < WEEKDAYPeriods[dayName]; j++) {
                Period period = new Period();
                period.setId((long) (dayNumber * WEEKDAYPeriods[dayName] + j));
                period.setDay(newDay);
                period.setTimeslot(newTimeslotList.get(j));
                newPeriodList.add(period);
                newPeriodMap.put(Arrays.asList(dayNumber, j), period);
                newDay.getPeriodList().add(period);
            }
        }

        private int calculatePeriodListSize(int dayListSize){

            int Fullweeks = dayListSize / 7;
            int extraDays = dayListSize % 7;


            int mondayCount    = Fullweeks + (extraDays >= 1 ? 1: 0);
            int tuesdayCount   = Fullweeks + (extraDays >= 2 ? 1: 0);
            int wednesdayCount = Fullweeks + (extraDays >= 3 ? 1: 0);
            int thursdayCount  = Fullweeks + (extraDays >= 4 ? 1: 0);
            int fridayCount    = Fullweeks + (extraDays >= 5 ? 1: 0);
            int saturdayCount  = Fullweeks + (extraDays >= 6 ? 1: 0);
            int sundayCount    = Fullweeks + (extraDays >= 7 ? 1: 0);

            int MondaySlots = mondayCount * WEEKDAYPeriods[MONDAY];
            int tuesdaySlots = tuesdayCount * WEEKDAYPeriods[TUESDAY];
            int wednesdaySlots = wednesdayCount * WEEKDAYPeriods[WEDNESDAY];
            int thursdaySlots = thursdayCount * WEEKDAYPeriods[THURSDAY];
            int fridaySlots = fridayCount * WEEKDAYPeriods[FRIDAY];
            int saturdaySlots = saturdayCount * WEEKDAYPeriods[SATURDAY];
            int sundaySlots = sundayCount * WEEKDAYPeriods[SUNDAY];

            int periodListSize = MondaySlots + tuesdaySlots + wednesdaySlots + thursdaySlots + fridaySlots + saturdaySlots + sundaySlots;

            return periodListSize;

        }

        private Map<List<Integer>, Period> createPeriodListAndDayListAndTimeslotList(
                CourseSchedule schedule, int dayListSize, int timeslotListSize) throws IOException {

             int periodListSize = dayListSize * timeslotListSize; // --> original
            Map<List<Integer>, Period> periodMap = new HashMap<List<Integer>, Period>(periodListSize);

            //int periodListSize = (dayListSize * timeslotListSize) - (getWednessdayTimeDifference(timeslotListSize) * getAmountOfWednesDays(dayListSize));
//            int periodListSize = calculatePeriodListSize(dayListSize); //christophe


//            Map<List<Integer>, Period> newPeriodMap = new HashMap<List<Integer>, Period>(periodListSize);
            List<Day> dayList = new ArrayList<Day>(dayListSize);
            for (int i = 0; i < dayListSize; i++) {
                Day day = new Day();
                day.setId((long) i);
                day.setDayIndex(i);
                day.setPeriodList(new ArrayList<Period>(timeslotListSize));

                //wanneer dag 0 een maandag is --> dag 2 is woensdag en veelvouden van 9 zijn woensdagen
                // woensdagen krijgen minder capaciteit (namelijk 4 uur )

//                day.setPeriodList(new ArrayList<Period>(WEEKDAYPeriods[GetDayNrOfTheWeek(i)]));
//                switch (GetDayNrOfTheWeek(i)){
//                    case 0:
//
//                }
//                if (GetDayNrOfTheWeek(i) == 0 ) {
//                    day.setPeriodList(new ArrayList<Period>(WEEKDAYPeriods[MONDAY]));         //initial capacity =  4 uur op MONDAY
//                }
//                else{
//                    day.setPeriodList(new ArrayList<Period>(timeslotListSize));         //initial capacity arraylist meegeven
//                }
                dayList.add(day);

            }
            schedule.setDayList(dayList);

//            List<Timeslot> newTimeslotList = new ArrayList<Timeslot>(timeslotListSize);
            List<Timeslot> timeslotList = new ArrayList<Timeslot>(timeslotListSize);

            for (int i = 0; i < timeslotListSize; i++) {
                Timeslot timeslot = new Timeslot();
                timeslot.setId((long) i);
                timeslot.setTimeslotIndex(i);
                timeslotList.add(timeslot);
//                newTimeslotList.add(timeslot);
            }
            schedule.setTimeslotList(timeslotList);

//            List<Period> newPeriodList = new ArrayList<Period>(periodListSize);
            List<Period> periodList = new ArrayList<Period>(periodListSize);
            for (int i = 0; i < dayListSize; i++) {
                Day day = dayList.get(i);
                for (int j = 0; j < timeslotListSize; j++) {
                    Period period = new Period();
                    period.setId((long) (i * timeslotListSize + j));
                    period.setDay(day);
                    period.setTimeslot(timeslotList.get(j));
                    periodList.add(period);
                    periodMap.put(Arrays.asList(i, j), period);
                    day.getPeriodList().add(period);
                }
//                Day newDay = dayList.get(i);

//                //rekening houden met woensdag
//                if (i > 0 && ((i % (7+2)) == 0 || i == 2) ) {
//
////                    //woensdag
//                    createPeriodListForDay(newDay, i, WEDNESDAY,  newPeriodList,  newTimeslotList,  newPeriodMap);
////                    for (int j = 0; j < WEEKDAYPeriods[WEDNESDAY]; j++) {
////                        Period period = new Period();
////                        period.setId((long) (i * WEEKDAYPeriods[WEDNESDAY] + j));
////                        period.setDay(newDay);
////                        period.setTimeslot(newTimeslotList.get(j));
////                        newPeriodList.add(period);
////                        newPeriodMap.put(Arrays.asList(i, j), period);
////                        newDay.getPeriodList().add(period);
////                    }
//                }
//                createPeriodListForDay(newDay, i, GetDayNrOfTheWeek(i),  newPeriodList,  newTimeslotList,  newPeriodMap);
//                if (GetDayNrOfTheWeek(i) == 0 ) {
//                    createPeriodListForDay(newDay, i, MONDAY,  newPeriodList,  newTimeslotList,  newPeriodMap);
//                }
//                else{
//                    for (int j = 0; j < timeslotListSize; j++) {
//                        Period period = new Period();
//                        period.setId((long) (i * timeslotListSize + j));
//                        period.setDay(newDay);
//                        period.setTimeslot(newTimeslotList.get(j));
//                        newPeriodList.add(period);
//                        newPeriodMap.put(Arrays.asList(i, j), period);
//                        newDay.getPeriodList().add(period);
//                    }
//                }
                //einde toevoeging woensdag



            }
            schedule.setPeriodList(periodList);
            return periodMap;
//            schedule.setPeriodList(newPeriodList);
//            return newPeriodMap;

        }

        private void readCurriculumList(CourseSchedule schedule,
                Map<String, Course> courseMap, int curriculumListSize) throws IOException {
            readEmptyLine();
            readConstantLine("CURRICULA:");
            List<Curriculum> curriculumList = new ArrayList<Curriculum>(curriculumListSize);
            for (int i = 0; i < curriculumListSize; i++) {
                Curriculum curriculum = new Curriculum();
                curriculum.setId((long) i);
                // Curricula: <CurriculumID> <# Courses> <MemberID> ... <MemberID>
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line);
                if (lineTokens.length < 2) {
                    throw new IllegalArgumentException("Read line (" + line
                            + ") is expected to contain at least 2 tokens.");
                }
                curriculum.setCode(lineTokens[0]);
                int coursesInCurriculum = Integer.parseInt(lineTokens[1]);
                if (lineTokens.length != (coursesInCurriculum + 2)) {
                    throw new IllegalArgumentException("Read line (" + line + ") is expected to contain "
                            + (coursesInCurriculum + 2) + " tokens.");
                }
                for (int j = 2; j < lineTokens.length; j++) {
                    Course course = courseMap.get(lineTokens[j]);
                    if (course == null) {
                        throw new IllegalArgumentException("Read line (" + line + ") uses an unexisting course("
                                + lineTokens[j] + ").");
                    }
                    course.getCurriculumList().add(curriculum);
                }
                curriculumList.add(curriculum);
            }
            schedule.setCurriculumList(curriculumList);
        }

        private void readUnavailablePeriodPenaltyList(CourseSchedule schedule, Map<String, Course> courseMap,
                Map<List<Integer>, Period> periodMap, int unavailablePeriodPenaltyListSize)
                throws IOException {
            readEmptyLine();
            readConstantLine("UNAVAILABILITY_CONSTRAINTS:");
            List<UnavailablePeriodPenalty> penaltyList = new ArrayList<UnavailablePeriodPenalty>(
                    unavailablePeriodPenaltyListSize);
            for (int i = 0; i < unavailablePeriodPenaltyListSize; i++) {
                UnavailablePeriodPenalty penalty = new UnavailablePeriodPenalty();
                penalty.setId((long) i);

                // Unavailability_Constraints: <CourseID> <Day> <Day_Period>
                String line = bufferedReader.readLine();
                String[] lineTokens = splitBySpacesOrTabs(line, 3);

                penalty.setCourse(courseMap.get(lineTokens[0]));
                int dayIndex = Integer.parseInt(lineTokens[1]);
                int timeslotIndex = Integer.parseInt(lineTokens[2]);
                Period period = periodMap.get(Arrays.asList(dayIndex, timeslotIndex));

                if (period == null) {
                    throw new IllegalArgumentException("Read line (" + line + ") uses an unexisting period("
                            + dayIndex + " " + timeslotIndex + ").");
                }
                penalty.setPeriod(period);
                penaltyList.add(penalty);



            }

            //todo unavailable days via ctt files
            //custom hardcoded unavailable days
            UnavailableDay unavailableDay = new UnavailableDay();
            int UnavailableDayListSize = 1;
            List<UnavailableDay> unavailableDayList = new ArrayList<UnavailableDay>(UnavailableDayListSize);

            String target = "01/03/2017";
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date =  df.parse(target);
                unavailableDay.setDay(1);
                unavailableDay.setDate(date);
                unavailableDay.setId((long) 0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            unavailableDayList.add(unavailableDay);


            schedule.setUnavailablePeriodPenaltyList(penaltyList);
            schedule.setUnavailableDayList(unavailableDayList);
        }

        private void createLectureList(CourseSchedule schedule) {
            List<Course> courseList = schedule.getCourseList();
            List<Lecture> lectureList = new ArrayList<Lecture>(courseList.size());
            long id = 0L;
            for (Course course : courseList) {
                for (int i = 0; i < course.getLectureSize(); i++) {
                    Lecture lecture = new Lecture();
                    lecture.setId(id);
                    id++;
                    lecture.setCourse(course);
                    lecture.setLectureIndexInCourse(i);
                    lecture.setLocked(false);
                    // Notice that we leave the PlanningVariable properties on null
                    lectureList.add(lecture);
                }
            }
            schedule.setLectureList(lectureList);
        }

    }

}

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("Course")
public class Course extends AbstractPersistable {



    public static enum CourseType {
        Hoorcollege, //2 uur
        Werkcollege; //3 uur bij voorkeur
    }


    private int urenPerBlok;

    private String code;

    private Teacher teacher;
    private int lectureSize;
    private CourseType courseType;
    private int LectureTime;
    private int minWorkingDaySize;          //  Lectures of the same course should be spread out into a minimum number of days.
    private boolean isPCNeeded = true;

    private List<Curriculum> curriculumList;
    private int studentSize;

    private List<String> courseDependencies = new ArrayList<String>();
    private Map<String,Integer> courseDependencyCount = new HashMap<String, Integer>();

    public String getCode() {
        return code;
    }

//    code = name of the course (JAVA_EE, ABAP etc...)
    public void setCode(String code) {
        this.code = code;
        if(code.toUpperCase().endsWith("_WK")){
            //3 uur per dag bij voorkeur (automatisch zo veel mogelijk in blok gegroepeerd)
            this.setCourseType(CourseType.Werkcollege);
            this.setUrenPerBlok(3);
        }
        else{
            //2 uur per dag bij voorkeur (automatisch zo veel mogelijk in blok gegroepeerd)
            this.setCourseType(CourseType.Hoorcollege);
            this.setUrenPerBlok(2);
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getLectureSize() {
        return lectureSize;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public int getUrenPerBlok() {
        return urenPerBlok;
    }

    public void setUrenPerBlok(int urenPerBlok) {
        this.urenPerBlok = urenPerBlok;
    }


    public void setLectureSize(int lectureSize) {
        this.lectureSize = lectureSize;
    }

    //test zelf toegevoegd
    public int getLectureTime() {
        return LectureTime;
    }

    public void setLectureTime(int lectureTime) {
        LectureTime = lectureTime;
    }

    // Lectures of the same course should be spread out into a minimum number of days.
    public int getMinWorkingDaySize() {
        return minWorkingDaySize;
    }

    // Lectures of the same course should be spread out into a minimum number of days.
    public void setMinWorkingDaySize(int minWorkingDaySize) {
        this.minWorkingDaySize = minWorkingDaySize;
    }

    public boolean isPCNeeded() {
        return isPCNeeded;
    }

    public void setPCNeeded(boolean PCNeeded) {
        isPCNeeded = PCNeeded;
    }

    //how many of the pc's are used in this lecuture (1 = 100% %)


    public List<Curriculum> getCurriculumList() {
        return curriculumList;
    }

    //custom
    public boolean addCurriculum(Curriculum curriculum){
        if(this.curriculumList.contains(curriculum)){
            return false;
        }
        this.curriculumList.add(curriculum);
        return  true;
    }



    public void setCurriculumList(List<Curriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }

    public int getStudentSize() {
        return studentSize;
    }

    public void setStudentSize(int studentSize) {
        this.studentSize = studentSize;
    }

    //dependencies voor parent courses (welke)
    public List<String> getCourseDependencies() {
        return courseDependencies;
    }

    public void setCourseDependencies(List<String> courseDependencies) {
        this.courseDependencies = courseDependencies;
    }

    //dependency count
    public Map<String, Integer> getCourseDependencyCount() {
        return courseDependencyCount;
    }

    public void setCourseDependencyCount(Map<String, Integer> courseDependencyCount) {
        this.courseDependencyCount = courseDependencyCount;
    }


    @Override
    public String toString() {
        return code;
    }


}

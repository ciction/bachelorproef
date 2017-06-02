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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;

@XStreamAlias("TeacherGroup")
public class TeacherGroup extends AbstractPersistable {

    String groupedTeachers;
    String individualTeacher;

    public String getGroupedTeachers() {
        return groupedTeachers;
    }

    public void setGroupedTeachers(String groupedTeachers) {
        this.groupedTeachers = groupedTeachers;
    }

    public String getIndividualTeacher() {
        return individualTeacher;
    }

    public void setIndividualTeacher(String individualTeacher) {
        this.individualTeacher = individualTeacher;
    }

    @Override
    public String toString() {
        return "TeacherGroup{" +
                "groupedTeachers='" + groupedTeachers + '\'' +
                ", individualTeacher='" + individualTeacher + '\'' +
                '}';
    }
}

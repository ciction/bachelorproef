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

package org.optaplanner.examples.curriculumcourse.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionExporter;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.common.persistence.SolutionDao;
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.examples.curriculumcourse.domain.CourseSchedule;
import org.optaplanner.examples.curriculumcourse.persistence.*;
import org.optaplanner.examples.curriculumcourse.swingui.CttEditorFrame;
import org.optaplanner.examples.curriculumcourse.swingui.CurriculumCoursePanel;

public class CurriculumCourseApp extends CommonApp<CourseSchedule> {

    public static final String SOLVER_CONFIG
            = "org/optaplanner/examples/curriculumcourse/solver/curriculumCourseSolverConfig.xml";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        //create windows
        CurriculumCourseApp curriculumCourseApp = new CurriculumCourseApp();
        curriculumCourseApp.init();
        //custom
        CttEditorFrame cttEditorFrame = new CttEditorFrame();
        curriculumCourseApp.AddFrame("CttEditor", cttEditorFrame);


    }

    public CurriculumCourseApp() {
        super("Course timetabling",
                "Official competition name: ITC 2007 track3 - Curriculum course scheduling\n\n" +
                        "Assign lectures to periods and rooms.",
                SOLVER_CONFIG,
                CurriculumCoursePanel.LOGO_PATH);
    }

    @Override
    protected SolutionPanel createSolutionPanel() {
        return new CurriculumCoursePanel();
    }

    @Override
    protected SolutionDao createSolutionDao() {
        return new CurriculumCourseDao();
    }

    @Override
    protected AbstractSolutionImporter[] createSolutionImporters() {
        return new AbstractSolutionImporter[]{
                new CurriculumCourseImporter()
//                new CurriculumCourseImporter_old()
        };
    }

    @Override
    protected AbstractSolutionExporter createSolutionExporter() {
        return new CurriculumCourseExporter();
    }

}

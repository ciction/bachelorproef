package org.optaplanner.examples.curriculumcourse.swingui;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.examples.common.swingui.components.Custom.JDropDownListPanel;
import org.optaplanner.examples.common.swingui.components.Custom.JInputFieldPanel;
import org.optaplanner.examples.common.swingui.components.Custom.JOkCancelPanel;
import org.optaplanner.examples.curriculumcourse.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Christophe on 3/6/2017.
 */
public class CttEditorFrame extends JFrame {

    //editor data
    List<Course> m_courseList;
    List<Room> m_roomList;
    List<Day> m_dayList;
    List<Period> m_periodList;
    List<Curriculum> m_curriculumList;
    List<UnavailablePeriodPenalty> m_penaltyList;

    int m_editorCourseCount = 0;
    int m_editorRoomCount = 0;
    int m_editorDayCount = 0;
    int m_editorPeriodCount = 0;
    int m_editorCurriculumCount = 0;
    int m_editorPenaltyCount = 0;

    //main window (this)
    JFrame editorFrame = null;
    Color m_backgroundColor = new Color(238, 238, 236);

    //first panel - general
    JPanel m_generalPanel;
    JPanel m_generalPeriodsPerDayPanel;
    JPanel m_generalDaypanel;

    JLabel m_NameLabel;
    JLabel m_CoursesLabel;
    JLabel m_RoomsLabel;
    JLabel m_DaysLabel;
    JTextField m_DaysTextField;
    JLabel m_Periods_per_dayLabel;
    JTextField m_Periods_per_dayField;
    JLabel m_CurriculaLabel;
    JLabel m_ConstraintsLabel;

    //second panel - courses
    JPanel m_courseContainerPanel;
    JScrollPane m_courseScrollPane;
    JPanel m_courseGridPanel;
    GridBagLayout m_courseGridBagLayout;
    JButton m_addCourseButton = new JButton("Add course");


    //third panel - rooms
    JPanel m_roomsContainerPanel;
    JScrollPane m_roomsScrollPane;
    JPanel m_roomsGridPanel;
    JButton m_addRoomButton = new JButton("Add room");


    //fourth panel - curriculum
    JPanel m_curriculumContainerPanel;
    JPanel m_curriculumGridPanel;
    JButton m_addCurriculumButton = new JButton("Add curriculum");


    //fifth panel - unavailability
    JPanel m_unavailableContainerPanel;
    JPanel m_unavailableGridPanel;
    JButton m_addPenaltyButton = new JButton("Add penalty");

    boolean m_isInitial = true;
    int m_courseGridRows = 0;


    public CttEditorFrame() throws HeadlessException {
        super("Ctt editor");
        editorFrame = this;

        BoxLayout mainBboxLayout = new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS);
        setLayout(mainBboxLayout);


        m_generalPanel = new JPanel();
        m_generalPanel.setLayout(new BoxLayout(m_generalPanel, BoxLayout.PAGE_AXIS));
        m_generalPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        m_generalPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(m_generalPanel);


        m_NameLabel = new JLabel();
        m_CoursesLabel = new JLabel();
        m_RoomsLabel = new JLabel();
        m_DaysLabel = new JLabel();
        m_DaysTextField = new JTextField();
        m_Periods_per_dayLabel = new JLabel();
        m_Periods_per_dayField = new JTextField();
        m_CurriculaLabel = new JLabel();
        m_ConstraintsLabel = new JLabel();


        m_generalPanel.add(m_NameLabel);
        m_generalPanel.add(m_CoursesLabel);
        m_generalPanel.add(m_RoomsLabel);

        //days
        m_generalDaypanel = new JPanel();
        m_generalDaypanel.setLayout(new BoxLayout(m_generalDaypanel, BoxLayout.X_AXIS));
        m_generalDaypanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        m_generalDaypanel.add(m_DaysLabel);
        m_generalDaypanel.add(m_DaysTextField);
        m_generalPanel.add(m_generalDaypanel);

        //periods per day
        m_generalPeriodsPerDayPanel = new JPanel();
        m_generalPeriodsPerDayPanel.setLayout(new BoxLayout(m_generalPeriodsPerDayPanel, BoxLayout.X_AXIS));
        m_generalPeriodsPerDayPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        m_generalPeriodsPerDayPanel.add(m_Periods_per_dayLabel);
        m_generalPeriodsPerDayPanel.add(m_Periods_per_dayField);
        m_generalPanel.add(m_generalPeriodsPerDayPanel);

        int lineWidth = 148;
        int lineHeight = 28;
        m_generalDaypanel.setMaximumSize(new Dimension(lineWidth, lineHeight));
        m_generalPeriodsPerDayPanel.setMaximumSize(new Dimension(lineWidth, lineHeight));

        m_generalPanel.add(m_CurriculaLabel);
        m_generalPanel.add(m_ConstraintsLabel);
        m_generalPanel.setBorder(
                BorderFactory.createTitledBorder("General")
        );


        //m_courseContainerPanel
        m_courseContainerPanel = new JPanel();
        m_courseContainerPanel.setLayout(new BorderLayout());
        m_courseContainerPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(m_courseContainerPanel);
        m_courseContainerPanel.setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), "Courses")
        );
        add(m_addCourseButton);
        //set button action
        m_addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourseAction();
            }
        });


        //m_roomsContainerPanel
        m_roomsContainerPanel = new JPanel();
        m_roomsContainerPanel.setLayout(new BorderLayout());
        m_roomsContainerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        m_roomsContainerPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(m_roomsContainerPanel);
        m_roomsContainerPanel.setBorder(
                BorderFactory.createTitledBorder("Rooms")
        );
        add(m_addRoomButton);
        //set button action
        m_addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoomAction();
            }
        });

        //m_curriculumContainerPanel
        m_curriculumContainerPanel = new JPanel();

//        m_curriculumContainerPanel.setLayout(new BoxLayout(m_curriculumContainerPanel, BoxLayout.PAGE_AXIS));
        m_curriculumContainerPanel.setLayout(new BorderLayout());
        m_curriculumContainerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        m_curriculumContainerPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(m_curriculumContainerPanel);
        m_curriculumContainerPanel.setBorder(
                BorderFactory.createTitledBorder("Curricula")
        );
        add(m_addCurriculumButton);
        //set button action
        m_addCurriculumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCurriculumAction();
            }
        });


        //m_unavailableContainerPanel
        m_unavailableContainerPanel = new JPanel();
//        m_unavailableContainerPanel.setLayout(new BoxLayout(m_unavailableContainerPanel, BoxLayout.PAGE_AXIS));
        m_unavailableContainerPanel.setLayout(new BorderLayout());
        m_unavailableContainerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        m_unavailableContainerPanel.setAlignmentX(LEFT_ALIGNMENT);
        add(m_unavailableContainerPanel);
        m_unavailableContainerPanel.setBorder(
                BorderFactory.createTitledBorder("Unavailability Constraints")
        );
        add(m_addPenaltyButton);

        super.pack();
        setLocationRelativeTo(null);

    }

    public void addCourseAction() {
        //create popup window
        final JFrame popup = new JFrame("Add course");
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //set vertical layout panel
        JPanel popupPane = new JPanel();
        popupPane.setLayout(new BoxLayout(popupPane, BoxLayout.PAGE_AXIS));
        popup.add(popupPane);

        //add all the fields stacked vertically
        //course field
        final JInputFieldPanel coursePane = new JInputFieldPanel("Course:");
        popupPane.add(coursePane);
        //teacher field
        final JInputFieldPanel teacherPane = new JInputFieldPanel("Teacher:");
        popupPane.add(teacherPane);
        //amount of lectures
        final JInputFieldPanel lectureCountPane = new JInputFieldPanel("Number of lectures:");
        popupPane.add(lectureCountPane);
        //minimum working days
        final JInputFieldPanel workingDaysPane = new JInputFieldPanel("Minimum spread working days:");
        popupPane.add(workingDaysPane);
        //StudentSize field
        final JInputFieldPanel StudentSizePane = new JInputFieldPanel("Amount of students:");
        popupPane.add(StudentSizePane);

        //Create the Ok & Cancel button
        final JOkCancelPanel OkCancelPane = new JOkCancelPanel(popup);
        popupPane.add(OkCancelPane);
        OkCancelPane.addOkAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (coursePane.getText() != null && teacherPane.getText() != null && lectureCountPane.getText() != null &&
                        workingDaysPane.getText() != null && StudentSizePane.getText() != null) {
                    Course course = new Course();
                    Teacher courseTeacher = new Teacher();

                    course.setCode(coursePane.getText());
                    courseTeacher.setCode(teacherPane.getText());
                    course.setTeacher(courseTeacher);
                    course.setLectureSize(Integer.parseInt(lectureCountPane.getText()));
                    course.setMinWorkingDaySize(Integer.parseInt(workingDaysPane.getText()));
                    course.setStudentSize(Integer.parseInt(StudentSizePane.getText()));

                    ++m_courseGridRows;
                    addGridRow(course);

                    //update the general fields
                    ++m_editorCourseCount;
                    updatePanels();
                }

                pack();
                popup.dispose();
            }
        });

        //pack popup and show
        popup.pack();
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);
    }

    public void addRoomAction() {
        //create popup window
        final JFrame popup = new JFrame("Add Room");
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //set vertical layout panel
        JPanel popupPane = new JPanel();
        popupPane.setLayout(new BoxLayout(popupPane, BoxLayout.PAGE_AXIS));
        popup.add(popupPane);

        //add all the fields stacked vertically

        //Room Name field
        final JInputFieldPanel roomNamePane = new JInputFieldPanel("Room Name:");
        popupPane.add(roomNamePane);
        //Room Capacity field
        final JInputFieldPanel roomCapacityPane = new JInputFieldPanel("Capacity:");
        popupPane.add(roomCapacityPane);


        //Create the Ok & Cancel button
        final JOkCancelPanel OkCancelPane = new JOkCancelPanel(popup);
        popupPane.add(OkCancelPane);
        OkCancelPane.addOkAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roomNamePane.getText() != null && roomCapacityPane.getText() != null) {
                    Room room = new Room();

                    room.setCode(roomNamePane.getText());
                    room.setCapacity(Integer.parseInt(roomCapacityPane.getText()));

                    addGridRow(room);

                    //update the general fields
                    ++m_editorRoomCount;
                    updatePanels();
                }

//                pack();
                popup.dispose();
            }
        });

        //pack popup and show
        popup.pack();
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);
    }

    public void addCurriculumAction() {
        //create popup window
        final JFrame popup = new JFrame("Add Curriculum");
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //set vertical layout panel
        JPanel popupPane = new JPanel();
        popupPane.setLayout(new BoxLayout(popupPane, BoxLayout.PAGE_AXIS));
        popup.add(popupPane);

        //add all the fields stacked vertically

        // Curriculum field
        final JInputFieldPanel curriculumNamePane = new JInputFieldPanel("Curriculum Name:");
        popupPane.add(curriculumNamePane);

        final JDropDownListPanel curriculumCourseSelectorPane = new JDropDownListPanel("Add courses:", m_courseList);
        popupPane.add(curriculumCourseSelectorPane);
        popup.pack();


        //Create the Ok & Cancel button
        final JOkCancelPanel OkCancelPane = new JOkCancelPanel(popup);
        popupPane.add(OkCancelPane);
        OkCancelPane.addOkAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (curriculumNamePane.getText() != null && curriculumCourseSelectorPane.getCourses() != null) {

                    Curriculum curriculum = new Curriculum();
                    curriculum.setCode(curriculumNamePane.getText());
                    DefaultListModel curriculumCourses = curriculumCourseSelectorPane.getCourses();


                    //add the new curriculum to existin courses
                    for (Course course : m_courseList) {
                        if (curriculumCourses.contains(course.getCode())) {
                            course.addCurriculum(curriculum);
                        }
                    }


                    addGridRow(curriculum);

                    //update the general fields
                    ++m_editorRoomCount;
                    updatePanels();
                }

                popup.dispose();

            }
    });

    //pack popup and show
        popup.pack();
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);
}


    public boolean isM_isInitial() {
        return m_isInitial;
    }

    public void setM_isInitial(boolean m_isInitial) {
        this.m_isInitial = m_isInitial;
    }

    public void loadData(Solution solution) {
        CourseSchedule courseSchedule = (CourseSchedule) solution;

        m_courseList = courseSchedule.getCourseList();
        m_roomList = courseSchedule.getRoomList();
        m_dayList = courseSchedule.getDayList();
        m_periodList = courseSchedule.getPeriodList();
        m_curriculumList = courseSchedule.getCurriculumList();
        m_penaltyList = courseSchedule.getUnavailablePeriodPenaltyList();

        m_editorCourseCount = m_courseList.size();
        m_editorRoomCount = m_roomList.size();
        m_editorDayCount = m_dayList.size();
        m_editorPeriodCount = m_periodList.size();
        m_editorCurriculumCount = m_curriculumList.size();
        m_editorPenaltyCount = m_penaltyList.size();
    }

    public void setGeneralFields() {
        m_CoursesLabel.setText("Courses: " + m_editorCourseCount);
        m_RoomsLabel.setText("Rooms: " + m_editorRoomCount);
        m_DaysLabel.setText("Days: ");
        m_DaysTextField.setText(String.valueOf(m_editorDayCount));
        m_Periods_per_dayLabel.setText("Periods per day: ");
        m_Periods_per_dayField.setText(String.valueOf(m_editorPeriodCount));
        m_CurriculaLabel.setText("Curricula: " + m_editorCurriculumCount);
        m_ConstraintsLabel.setText("Constraints: " + m_editorPenaltyCount);
    }

    public void initCourseContainer() {
        //course panel
        m_courseGridBagLayout = new GridBagLayout();
        m_courseGridPanel = new JPanel(m_courseGridBagLayout);

        //add courses to grid
        for (Course course : m_courseList) {
            addGridRow(course);
            ++m_courseGridRows;
        }

        //add void container to fix scrolling
        m_courseGridPanel.add(getVoidPanelScrollFix());

        //add scrollbar
        m_courseContainerPanel.add(m_courseGridPanel);
        m_courseScrollPane = new JScrollPane(m_courseGridPanel);
        m_courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        m_courseContainerPanel.add(m_courseScrollPane);
    }

    public JPanel getVoidPanelScrollFix() {
        //add void container to fix scrolling
        JPanel innerVoidPanel = new JPanel();
        innerVoidPanel.setBackground(m_backgroundColor);
        GridBagConstraints courseGridBagConstraints = new GridBagConstraints();
        courseGridBagConstraints.weighty = 1.0;
        courseGridBagConstraints.fill = GridBagConstraints.VERTICAL;
        m_courseGridBagLayout.setConstraints(innerVoidPanel, courseGridBagConstraints);

        return innerVoidPanel;
    }

    public void initRoomsContainer() {

        //rooms panel
        m_roomsGridPanel = new JPanel(new GridLayout(0, 3));
        for (Room room : m_roomList) {
            addGridRow(room);
        }
        m_roomsGridPanel.setMaximumSize(new Dimension(300, 100));
        m_roomsContainerPanel.add(m_roomsGridPanel);
        m_roomsScrollPane = new JScrollPane(m_roomsGridPanel);
        m_roomsContainerPanel.add(m_roomsScrollPane);
    }

    public void initCurriculumContainer() {
        //curriculum panel (rows, cols)
        m_curriculumGridPanel = new JPanel(new GridLayout(m_curriculumList.size(), 3));
        for (Curriculum curriculum : m_curriculumList) {
            addGridRow(curriculum);
        }
        m_curriculumGridPanel.setMaximumSize(m_curriculumGridPanel.getPreferredSize());
        m_curriculumContainerPanel.add(m_curriculumGridPanel);
    }

    public void initUnavailableContainer() {
        //m_unavailableGridPanel panel (rows, cols)
        m_unavailableGridPanel = new JPanel(new GridLayout(m_penaltyList.size(), 3));
        for (UnavailablePeriodPenalty penalty : m_penaltyList) {
            addGridRow(penalty);
        }
        m_unavailableGridPanel.setMaximumSize(m_unavailableGridPanel.getPreferredSize());
        m_unavailableContainerPanel.add(m_unavailableGridPanel);
    }

    public void initFields(Solution solution) {
        loadData(solution);
        setGeneralFields();
        initCourseContainer();
        initRoomsContainer();
        initCurriculumContainer();
        initUnavailableContainer();

        m_courseContainerPanel.setPreferredSize(m_courseContainerPanel.getPreferredSize());
        super.pack();
        m_isInitial = false;

    }


    private void addGridRow(Object obj) {
//        final JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        if (obj instanceof Course) {
            Course course = (Course) obj;

            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 0.0;
            c.weighty = 0.0;
            c.gridx = 0;
            c.gridy = m_courseGridRows;
            m_courseGridPanel.add(new JLabel(course.getCode()), c);
            c.gridx = 1;
            c.gridy = m_courseGridRows;
            m_courseGridPanel.add(new JLabel(" " + course.getTeacher().getCode()), c);
            c.gridx = 2;
            c.gridy = m_courseGridRows;
            m_courseGridPanel.add(new JLabel("  " + String.valueOf(course.getLectureSize())), c);
            c.gridx = 3;
            c.gridy = m_courseGridRows;
            m_courseGridPanel.add(new JLabel(" " + String.valueOf(course.getMinWorkingDaySize())), c);
            c.gridx = 4;
            c.gridy = m_courseGridRows;
            ;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.fill = GridBagConstraints.BOTH;
            m_courseGridPanel.add(new JLabel(" " + String.valueOf(course.getStudentSize())), c);


            addRemoveButton(m_courseContainerPanel, m_courseGridPanel, course);
        } else if (obj instanceof Room) {
            Room room = (Room) obj;
            m_roomsGridPanel.add(new JLabel(room.getCode()));
            m_roomsGridPanel.add(new JLabel(" " + room.getCapacity()));
            addRemoveButton(m_roomsContainerPanel, m_roomsGridPanel, room);

        } else if (obj instanceof Curriculum) {
            Curriculum curriculum = (Curriculum) obj;
            //curriculum title

            JLabel curriculumLabel = new JLabel(curriculum.getCode() + "(" + curriculum.getCoursesInCurriculum() + ")");
            Font font = curriculumLabel.getFont();
            // same font but bold
            Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
            curriculumLabel.setFont(boldFont);
            m_curriculumGridPanel.add(curriculumLabel);

            //courses in curriculum
            for (Course course : m_courseList) {
                if (course.getCurriculumList().contains(curriculum)) {
                    JLabel component = new JLabel(" " + course.getCode());
                    m_curriculumGridPanel.add(component);
                }
            }
            addRemoveButton(m_curriculumContainerPanel, m_curriculumGridPanel, curriculum);

        } else if (obj instanceof UnavailablePeriodPenalty) {
            UnavailablePeriodPenalty penalty = (UnavailablePeriodPenalty) obj;
            m_unavailableGridPanel.add(new JLabel(penalty.getCourse().getCode()));
            m_unavailableGridPanel.add(new JLabel(" day: " + penalty.getPeriod().getDay().toString()));
            m_unavailableGridPanel.add(new JLabel(" timeslot: " + penalty.getPeriod().getTimeslot().toString()));

        }

    }

    private void addRemoveButton(final JPanel parent, final JPanel panel, final Object obj) {
        //remove button
        JButton removeButton = new JButton("remove");


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = 0;
                JPanel gridPanel = null;
                List currentList = null;

                if (obj instanceof Course) {
                    gridPanel = m_courseGridPanel;
                    currentList = m_courseList;

                    Course course = (Course) obj;
                    row = currentList.indexOf(course);
                    currentList.remove(course);
                }
                if (obj instanceof Room) {
                    gridPanel = m_roomsGridPanel;
                    currentList = m_roomList;

                    Room room = (Room) obj;
                    row = currentList.indexOf(room);
                    currentList.remove(room);
                }

                updatePanels();

                if (obj instanceof Course) {

                } else {
                    int columns = ((GridLayout) gridPanel.getLayout()).getColumns();
                    for (int i = row * 6; i < row * 6 + columns; i++) {
                        gridPanel.remove(row * 6);
                    }
                    if (currentList.size() > 0) {
                        gridPanel.setMaximumSize(gridPanel.getPreferredSize());
                        parent.revalidate();
                        parent.repaint();
                    }
                }

            }
        });

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 5;
        c.gridy = m_courseGridRows;
        panel.add(removeButton, c);
    }

    private void updatePanels() {
        m_CoursesLabel.setText("Courses: " + m_editorCourseCount);
        m_RoomsLabel.setText("Rooms: " + m_editorRoomCount);
        m_DaysLabel.setText("Days: ");
        m_DaysTextField.setText(String.valueOf(m_editorDayCount));
        m_Periods_per_dayLabel.setText("Periods per day: ");
        m_Periods_per_dayField.setText(String.valueOf(m_editorPeriodCount));
        m_CurriculaLabel.setText("Curricula: " + m_editorCurriculumCount);
        m_ConstraintsLabel.setText("Constraints: " + m_editorPenaltyCount);
    }
}

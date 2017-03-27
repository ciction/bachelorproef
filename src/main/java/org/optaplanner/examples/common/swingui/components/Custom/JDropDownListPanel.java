package org.optaplanner.examples.common.swingui.components.Custom;

import org.optaplanner.examples.curriculumcourse.domain.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Christophe on 3/27/2017.
 */
public class JDropDownListPanel extends JPanel implements ActionListener {

    /*
    |------------------------------------------|
    | |------------|                           |
    | |  [list 1]  |      [dropdown selector]  |        2 rows and 2 columns  ==>
    | |  [list 1]  |                           |
    | |  [list 1]  |                           |
    | |------------|                           |
    |                                          |
    |  [Add button]           [Remove button]  |
    |------------------------------------------|
  */


    JPanel firstRowPane = new JPanel();
    JPanel secondRowPane = new JPanel();
    JPanel thirdRowPane = new JPanel();

    //label
    JLabel m_label = new JLabel("");

    //list
    JList m_list = null;
    DefaultListModel m_listModel = new DefaultListModel();

    //comboBox
    JComboBox m_comboBox = new JComboBox();
    List<String> m_comboBoxValues = new ArrayList<String>();
    String m_selectedString = null;

    //buttons
    final JButton m_addButton = new JButton("Add");
    final JButton m_removeButton = new JButton("Remove");
    final JButton m_resetButton = new JButton("Reset");


    public JDropDownListPanel(String title, List<Course>  courses) {
        super();
        //create box layout grid
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        firstRowPane.setLayout(new BoxLayout(firstRowPane, BoxLayout.LINE_AXIS));
        secondRowPane.setLayout(new BoxLayout(secondRowPane, BoxLayout.LINE_AXIS));
        thirdRowPane.setLayout(new BoxLayout(thirdRowPane, BoxLayout.LINE_AXIS));
        this.add(firstRowPane);
        this.add(secondRowPane);
        this.add(thirdRowPane);


        //add label
        m_label = new JLabel(title);
        firstRowPane.add(m_label);

        //add the list
        createList(courses);
        secondRowPane.add(m_list);

        //add dropdown combobox
        for (Course course : courses) {
            m_comboBoxValues.add(course.getCode());
        }

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        m_comboBox = new JComboBox(m_comboBoxValues.toArray());
//        m_comboBox.setSelectedIndex(0);
        m_comboBox.addActionListener(this);
        secondRowPane.add(m_comboBox);


        //buttons
        thirdRowPane.add(m_addButton);
        m_addButton.addActionListener(this);


        thirdRowPane.add(m_removeButton);
        m_removeButton.addActionListener(this);

        thirdRowPane.add(m_resetButton);
        m_resetButton.addActionListener(this);
    }


    private void createList(List<Course> courses) {
        //create datamodel
        for (Course course : courses) {
            m_listModel.addElement(course.getCode());
        }

        m_list = new JList(m_listModel); //data has type Object[]
        m_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        m_list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        m_list.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(m_list);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private void addListItem() {
        m_listModel.addElement(m_selectedString);
    }

    public DefaultListModel getCourses(){
        return m_listModel;
    }

    //Actions
    @Override
    public void actionPerformed(ActionEvent e) {
        //check the source of the action

        //if it's the dropdown
        if (e.getSource().getClass().equals(JComboBox.class)) {
            JComboBox cb = (JComboBox) e.getSource();
            String selectedString = (String) cb.getSelectedItem();
            m_selectedString = selectedString;
        }

        //if it's a button
        if (e.getSource().getClass().equals(JButton.class)) {
            JButton button = (JButton) e.getSource();

            //Add button
            String buttonText = button.getText();
            if (button == m_addButton) {
                if (!m_listModel.contains(m_selectedString)) {
                    m_listModel.addElement(m_selectedString);
                }
            }
            //Remove button
            else if (button == m_removeButton) {
                int index = m_list.getSelectedIndex();
                if(index > -1){
                    m_listModel.remove(index);
                }
            }
            //reset button
            else if (button == m_resetButton) {
                m_listModel.removeAllElements();
            }
        }

    }


}

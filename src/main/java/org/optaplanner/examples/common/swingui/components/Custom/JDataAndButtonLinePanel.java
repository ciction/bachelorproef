package org.optaplanner.examples.common.swingui.components.Custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Christophe on 3/27/2017.
 */
public class JDataAndButtonLinePanel extends  JPanel{
     /*
    |-----------------------------|
    |  [dataPanel]     [button]   |
    |-----------------------------|
  */

    JButton m_button = new JButton();
    JPanel m_dataPane = new JPanel();


    public JDataAndButtonLinePanel(JPanel dataPanel, String buttonText) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        m_dataPane.setLayout(new BoxLayout(m_dataPane, BoxLayout.LINE_AXIS));
        m_dataPane = dataPanel;

        m_button = new JButton(buttonText);
        m_button.setAlignmentX(Component.RIGHT_ALIGNMENT);

        this.add(m_dataPane);
        this.add(m_button);
    }

    public void addDataComponent(JComponent dataComponent){
        m_dataPane.add(dataComponent);
    }

    public  void addButtonListener(ActionListener actionListener){
        m_button.addActionListener(actionListener);
    }



}

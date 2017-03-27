package org.optaplanner.examples.common.swingui.components.Custom;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Christophe on 3/24/2017.
 */
public class JInputFieldPanel extends JPanel {
    /*
    |-----------------------------|
    |  Label:      [textfield]    |
    |-----------------------------|
  */


    final JTextField m_textfield = new JTextField();
    JLabel m_label;

    public JInputFieldPanel(String labelText) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        m_label = new JLabel(labelText);
        m_textfield.setMinimumSize(new Dimension(100,20));
        this.add(m_label);
        this.add(m_textfield);
    }

    public String getText(){
        return m_textfield.getText();
    }
}

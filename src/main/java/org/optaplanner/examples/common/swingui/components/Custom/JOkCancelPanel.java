package org.optaplanner.examples.common.swingui.components.Custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Christophe on 3/24/2017.
 */
public class JOkCancelPanel extends JPanel {
    /*
    |-----------------------------|
    |                Cancel Ok    |
    |-----------------------------|
  */
    final JButton m_cancelButton =  new JButton("Cancel");
    final JButton m_okButton = new JButton("Ok");

    //Lay out the buttons from left to right.
    public JOkCancelPanel(final JFrame parentWindow) {
        super();

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.add(m_cancelButton);
        this.add(Box.createRigidArea(new Dimension(10, 0)));
        this.add(m_okButton);

        m_cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentWindow.dispose();
            }
        });
    }

    public  void addOkAction(ActionListener actionListener){
        m_okButton.addActionListener(actionListener);
    }
}



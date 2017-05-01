package org.optaplanner.examples.common.swingui.components.Custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Christophe on 3/27/2017.
 */
public class JTitleDetailsPanelHorizontal extends  JPanel{
     /*
    |-----------------------------|
    |  [title]     [details]      |
    |-----------------------------|
  */

    JPanel m_titlePane = new JPanel();
    JPanel m_detailsPane = new JPanel();


    public JTitleDetailsPanelHorizontal() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        m_titlePane.setLayout(new BoxLayout(m_titlePane, BoxLayout.LINE_AXIS));
        m_detailsPane.setLayout(new BoxLayout(m_detailsPane, BoxLayout.LINE_AXIS));

        this.add(m_titlePane);
        this.add(m_detailsPane);
    }

    public void addTitleComponent(JComponent titleComponent){
        Font font = titleComponent.getFont();
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        titleComponent.setFont(boldFont);

        m_titlePane.add(titleComponent);
    }

    public void addDetailsComponent(JComponent detailsComponent){
        m_detailsPane.add(detailsComponent);
    }



}

// $Id$
// Copyright (c) 2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
package org.argouml.argopdf.ui;

import org.argouml.kernel.Project;
import org.argouml.model.Model;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.uml.diagram.use_case.ui.UMLUseCaseDiagram;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * TreeRenderer is the implementation of a TreeCellRenderer for a TreeNode
 *
 * @author Dzmitry Churbanau
 * @version 1.0
 */
public class TreeRenderer extends JPanel implements TreeCellRenderer {

    private JCheckBox check = new JCheckBox();
    private TreeLabel label = new TreeLabel();

    public TreeRenderer() {
        setLayout(null);
        setBackground(Color.WHITE);
        check.setBackground(Color.WHITE);
        add(check);
        add(label);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected,
                                                  boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        //todo experimentation; must be reimplemented
        Object userObject = ((TreeNode)value).getUserObject();
        String stringValue = "undefined value";
        if(userObject instanceof Project) {
            stringValue = ((Project)userObject).getBaseName();
            Object model = ((Project)userObject).getModel();
            if(model != null) {
                Icon icon = ResourceLoaderWrapper.getInstance().lookupIcon(model);
                if (icon != null) {
                    label.setIcon(icon);
                }
            }
        } else {
            if(userObject instanceof UMLUseCaseDiagram) {
                stringValue = ((UMLUseCaseDiagram)userObject).getName();
                Icon icon = ResourceLoaderWrapper.getInstance().lookupIcon(userObject);
                if (icon != null) {
                    label.setIcon(icon);
                }
            }
        }

        check.setSelected(((TreeNode) value).isSelected());
        label.setText(stringValue);
        
        return this;
    }

    public Dimension getPreferredSize() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        return new Dimension(d_check.width + d_label.width,
            (d_check.height < d_label.height ? d_label.height
                : d_check.height));
    }    

    public void doLayout() {
        Dimension d_check = check.getPreferredSize();
        Dimension d_label = label.getPreferredSize();
        int y_check = 0;
        int y_label = 0;
        if (d_check.height < d_label.height) {
          y_check = (d_label.height - d_check.height) / 2;
        } else {
          y_label = (d_check.height - d_label.height) / 2;
        }

        check.setLocation(0, y_check);
        check.setBounds(0, y_check, d_check.width, d_check.height);
        label.setLocation(d_check.width, y_label);
        label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
    }
}
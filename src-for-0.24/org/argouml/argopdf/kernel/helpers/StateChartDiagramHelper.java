/* $Id$
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nett
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

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
package org.argouml.argopdf.kernel.helpers;

import org.apache.log4j.Logger;
import org.omg.uml.foundation.core.UmlClass;
import org.argouml.i18n.Translator;
import org.argouml.kernel.ProjectManager;
import org.argouml.uml.diagram.state.ui.UMLStateDiagram;
import org.argouml.argopdf.kernel.ReportUtils;
import org.argouml.ui.ArgoDiagram;
import com.lowagie.text.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Contains helper methods for processing state chart diagrams
 *
 * @author Dmitry Churbanau
 * @version 0.1
 */
public class StateChartDiagramHelper {

    private static final Logger LOG = Logger.getLogger(StateChartDiagramHelper.class);

    /**
     * Returns all state chart diagrams of uml class
     *
     * @param umlClass an instance of <i>UmlClass</i> class
     * @return a List which containsall state chart diagrams of uml class
     */
    public static List getStateChartDiagrams(UmlClass umlClass) {
        List stateChartD = new ArrayList();

        Vector diagrams  = ProjectManager.getManager().getCurrentProject().getDiagrams();
        for(Object el : diagrams) {

            if(el instanceof UMLStateDiagram && umlClass.equals(((UMLStateDiagram)el).getNamespace())) {
                stateChartD.add(el);
            }

        }

        return stateChartD;
    }

    /**
     * Generates state chart diagrams of uml class object
     *
     * @param document current document instance
     * @param section  section, to which state chart diagram will be added
     * @param umlClass an instance of <i>UmlClass</i> class
     * @param generateDiagrams defines, whether diagram image should be generated
     */
    public static void generateStateChartDiagrams(Document document, Section section, UmlClass umlClass, boolean generateDiagrams) {
        List diagrams = getStateChartDiagrams(umlClass);

        if(diagrams.size() > 0) {
            Section subSect = null;

            for(Object el : diagrams) {

                Image im = ReportUtils.makeImageOfDiagram((ArgoDiagram)el);
                if(im != null && generateDiagrams) {
                    if(subSect == null) {
                        subSect = section.addSection("", 0);
                        subSect.setBookmarkTitle(Translator.localize("argopdf.report.diagram.statechart"));
                        subSect.add(ReportUtils.generateTitle(Translator.localize("argopdf.report.diagram.statechart_s"), 2, false));
                    }
                    ReportUtils.adjustImageSizeToDocumentPageSize(im,  document);
                    subSect.add(ReportUtils.generateTitle(ReportUtils.getElementName(el), 3, false));
                    subSect.add(Chunk.NEWLINE);
                    subSect.add(new Chunk(im, 0, 0, true));
                    subSect.add(Chunk.NEWLINE);
                }
            }

        }
    }
}

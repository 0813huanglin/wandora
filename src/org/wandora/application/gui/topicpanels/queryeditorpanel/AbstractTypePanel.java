/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2014 Wandora Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.wandora.application.gui.topicpanels.queryeditorpanel;

import java.awt.Container;
import javax.swing.JPanel;
import org.wandora.query2.DirectiveUIHints.Parameter;

/**
 *
 * @author olli
 */


public abstract class AbstractTypePanel extends JPanel {
    
    protected Parameter parameter;
    
    public AbstractTypePanel(Parameter parameter){
        this.parameter=parameter;
    }
    
    public abstract void setLabel(String label);
    
    public void disconnect(){
        
    }
    
    protected QueryEditorComponent getEditor(){
        Container parent=getParent();
        while(parent!=null && !(parent instanceof QueryEditorComponent)){
            parent=parent.getParent();
        }
        if(parent!=null) return (QueryEditorComponent)parent;
        else return null;
    }    
    
    protected DirectivePanel getDirectivePanel(){
        Container parent=getParent();
        while(parent!=null && !(parent instanceof DirectivePanel)){
            parent=parent.getParent();
        }
        if(parent!=null) return (DirectivePanel)parent;
        else return null;
    }    
    
    public abstract Object getValue();
    public abstract String getValueScript();
    public Parameter getParameter(){
        return parameter;
    }
}

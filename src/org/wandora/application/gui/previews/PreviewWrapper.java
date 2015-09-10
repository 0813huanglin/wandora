/*
 * WANDORA
 * Knowledge Extraction, Management, and Publishing Application
 * http://wandora.org
 * 
 * Copyright (C) 2004-2015 Wandora Team
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
 * 
 * PreviewWrapper.java
 *
 * Created on 29. toukokuuta 2006, 14:55
 *
 */

package org.wandora.application.gui.previews;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import org.wandora.topicmap.Locator;



/**
 *
 * @author akivela
 */
public class PreviewWrapper extends JPanel {
    private static HashMap<Object,PreviewWrapper> previewWrappers = null;
    
    private PreviewPanel currentPanel = null;
    private Component currentUI = null;
    private Locator currentLocator = null;
    
    
    
    /**
     * Creates a new instance of PreviewWrapper
     */
    private PreviewWrapper() {
        this.setLayout(new BorderLayout());
    }
    
    
    
    public static PreviewWrapper getPreviewWrapper(Object owner) {
        if(previewWrappers == null) {
            previewWrappers = new HashMap();
        }
        PreviewWrapper previewWrapper = previewWrappers.get(owner);
        if(previewWrapper == null) {
            previewWrapper = new PreviewWrapper();
            previewWrappers.put(owner, previewWrapper);
        }
        return previewWrapper;
    }
    
    
    public static void removePreviewWrapper(Object owner) {
        if(previewWrappers != null) {
            previewWrappers.remove(owner);
        }
    }
    
    
    
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    
    
    public void stop() {
        if(currentPanel != null) {
            // System.out.println("Stopping preview wrapper.");
            currentPanel.stop();
            currentPanel = null;
            currentLocator = null;
        }
    }
    
    
    public void setURL(final Locator subjectLocator) {
        if(subjectLocator != null && subjectLocator.equals(currentLocator)) {
            return;
        }
        
        if(currentPanel != null) {
            currentPanel.stop();
            currentPanel.finish();
            currentPanel = null;
            currentUI = null;
        }
        
        removeAll();

        currentLocator = subjectLocator;
        if(subjectLocator == null)
            return;

        if(subjectLocator.toExternalForm().equals(""))
            return;

        final PreviewWrapper finalThis = this;

        try {
            currentPanel = PreviewFactory.create(subjectLocator);
            if(currentPanel != null) {
                String locatorString = subjectLocator.toExternalForm();
                if(locatorString.length() > 50) locatorString = locatorString.substring(0,50)+"...";
                System.out.println("Created preview "+currentPanel.getClass()+" for "+locatorString);
            }
        }
        catch(Exception e) {
            PreviewUtils.previewError(finalThis, "Creating preview failed.", e);
        }

        if(currentPanel != null) {
            currentUI = currentPanel.getGui();
            if(currentUI != null) {
                add(currentUI, BorderLayout.CENTER);
                setPreferredSize(currentUI.getPreferredSize());
            }
        }
        revalidate();

    }
    
    
    // -------------------------------------------------------------------------
    
    
    @Override
    public Dimension getPreferredSize() {
        if(currentUI != null) {
            return currentUI.getPreferredSize();
        }
        else {
            return new Dimension(0,0);
        }
    }
    
    
    @Override
    public Dimension getMinimumSize() {
        if(currentUI != null) {
            return currentUI.getMinimumSize();
        }
        else {
            return new Dimension(0,0);
        }
    }
    
    
    /*
    @Override
    public Dimension getMaximumSize() {
        if(currentUI != null) {
            return currentUI.getMaximumSize();
        }
        else {
            return new Dimension(0,0);
        }
    }
    */
}

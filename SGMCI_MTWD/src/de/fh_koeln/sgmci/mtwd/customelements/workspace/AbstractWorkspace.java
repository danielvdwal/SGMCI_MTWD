package de.fh_koeln.sgmci.mtwd.customelements.workspace;

import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class AbstractWorkspace extends MTRectangle implements IWorkspace {
    
    final MTRectangle workspace;
    final MTSvgButton addWorkspaceButton;
    final MTSvgButton closeButton;
    
    public AbstractWorkspace(PApplet pApplet, float width, float height, boolean light) {
        super(pApplet, width, height);
        this.workspace = new MTRectangle(pApplet, width, height);
        this.workspace.setNoFill(true);
        this.workspace.setNoStroke(true);
        this.workspace.setPickable(false);
        this.workspace.removeAllGestureEventListeners();
        this.workspace.unregisterAllInputProcessors();
        
        if(light) {
            this.addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonLightSvgFile);
            this.closeButton = new MTSvgButton(pApplet, closeButtonLightSvgFile);
        } else {
            this.addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonSvgFile);
            this.closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        }
        
        addChild(addWorkspaceButton);
        addChild(workspace);
        workspace.setVisible(false);
    }
    
    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        workspace.setVisible(active);
    }

    public MTSvgButton getAddWorkspaceButton() {
        return addWorkspaceButton;
    }

    public MTSvgButton getCloseButton() {
        return closeButton;
    }
}

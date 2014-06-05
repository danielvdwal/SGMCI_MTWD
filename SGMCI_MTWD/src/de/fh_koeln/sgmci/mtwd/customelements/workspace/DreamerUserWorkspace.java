package de.fh_koeln.sgmci.mtwd.customelements.workspace;

import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import de.fh_koeln.sgmci.mtwd.customelements.Cloud;
import de.fh_koeln.sgmci.mtwd.customelements.SplitKeyboard;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class DreamerUserWorkspace extends AbstractWorkspace {

    private final AbstractKeyboard keyboard;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
    private final MTSvgButton readyButton;
    private final MTSvgButton readyButtonDone;
    private final Cloud cloud;

    public DreamerUserWorkspace(PApplet pApplet) {
        super(pApplet, 916, 200, false);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        keyboard = new SplitKeyboard(pApplet);
        helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
        readyButton = new MTSvgButton(pApplet, readyButtonSvgFile);
        readyButtonDone = new MTSvgButton(pApplet, readyButtonDoneSvgFile);
        cloud = new Cloud(pApplet, true);
        cloud.getTextArea().setEnableCaret(true);
        cloud.removeAllGestureEventListeners();
        cloud.unregisterAllInputProcessors();

        positionAllComponents();
    }

    public void setIsReady(boolean ready) {
        readyButton.setVisible(!ready);
        readyButtonDone.setVisible(ready);
    }

    public MTSvgButton getHelpButton() {
        return helpButton;
    }

    public MTSvgButton getProblemButton() {
        return problemButton;
    }

    public MTSvgButton getReadyButton() {
        return readyButton;
    }

    public MTSvgButton getReadyButtonDone() {
        return readyButtonDone;
    }

    public Cloud getCloud() {
        return cloud;
    }

    private void positionAllComponents() {
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        workspace.addChild(keyboard);
        keyboard.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        keyboard.addChild(helpButton);
        keyboard.addChild(problemButton);
        keyboard.addChild(closeButton);
        keyboard.addChild(readyButton);
        keyboard.addChild(readyButtonDone);
        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        problemButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        readyButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        readyButtonDone.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), helpButton.getHeightXYRelativeToParent() - 30));
        problemButton.setPositionRelativeToParent(new Vector3D(-problemButton.getWidthXYRelativeToParent(), keyboard.getHeight() - problemButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + closeButton.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));
        readyButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + readyButton.getWidthXYRelativeToParent(), keyboard.getHeight() - readyButton.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + readyButtonDone.getWidthXYRelativeToParent(), keyboard.getHeight() - readyButtonDone.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setVisible(false);
        
        keyboard.addChild(cloud);
        cloud.setPositionRelativeToParent(new Vector3D(keyboard.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        keyboard.addTextInputListener(cloud.getTextArea());
    }
}

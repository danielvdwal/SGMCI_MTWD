package de.fh_koeln.sgmci.mtwd.customelements;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class DreamerUserWorkplace extends MTRectangle {

    private static final String addWorkspaceButtonSvgFile = "data/plusButton.svg";
    private static final String helpButtonSvgFile = "data/helpButton.svg";
    private static final String problemButtonSvgFile = "data/problemButton.svg";
    private static final String closeButtonSvgFile = "data/closeButton.svg";
    private static final String readyButtonSvgFile = "data/readyButton.svg";
    private static final String readyButtonDoneSvgFile = "data/readyButtonDone.svg";
    private static final float buttonScaleFactor = 1.4f;

    private final MTSvgButton addWorkspaceButton;
    private final AbstractKeyboard keyboard;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
    private final MTSvgButton closeButton;
    private final MTSvgButton readyButton;
    private final MTSvgButton readyButtonDone;
    private final Cloud cloud;

    public DreamerUserWorkplace(PApplet pApplet) {
        super(916, AbstractKeyboard.KEYBOARD_HEIGHT, pApplet);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonSvgFile);
        keyboard = new SplitKeyboard(pApplet);
        helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
        closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        readyButton = new MTSvgButton(pApplet, readyButtonSvgFile);
        readyButtonDone = new MTSvgButton(pApplet, readyButtonDoneSvgFile);
        cloud = new Cloud(pApplet, true);
        cloud.getTextArea().setEnableCaret(true);
        cloud.removeAllGestureEventListeners();
        cloud.unregisterAllInputProcessors();
        
        positionAllComponents();
    }

    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        keyboard.setVisible(active);
    }

    public void setIsReady(boolean ready) {
        readyButton.setVisible(!ready);
        readyButtonDone.setVisible(ready);
    }

    public MTSvgButton getAddWorkspaceButton() {
        return addWorkspaceButton;
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

    public MTSvgButton getCloseButton() {
        return closeButton;
    }

    public Cloud getCloud() {
        return cloud;
    }

    private void positionAllComponents() {
        addChild(addWorkspaceButton);
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        addChild(keyboard);
        keyboard.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));
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
        keyboard.setVisible(false);

        keyboard.addChild(cloud);
        cloud.setPositionRelativeToParent(new Vector3D(keyboard.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        keyboard.addTextInputListener(cloud.getTextArea());
        
    }
}

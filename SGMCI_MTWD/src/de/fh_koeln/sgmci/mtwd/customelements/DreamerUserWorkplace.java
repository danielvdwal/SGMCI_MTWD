package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
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
    private final MTSvgButton readyButton;
    private final MTSvgButton readyButtonDone;
    private final MTSvgButton closeButton;
    private final MTRectangle textAreaBackground;
    private final MTTextArea textArea;
    private final MTRectangle sendButton;

    public DreamerUserWorkplace(PApplet pApplet) {
        super(916, AbstractKeyboard.KEYBOARD_HEIGHT, pApplet);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        addWorkspaceButton = new MTSvgButton(addWorkspaceButtonSvgFile, pApplet);
        keyboard = new SplitKeyboard(pApplet);
        helpButton = new MTSvgButton(helpButtonSvgFile, pApplet);
        problemButton = new MTSvgButton(problemButtonSvgFile, pApplet);
        closeButton = new MTSvgButton(closeButtonSvgFile, pApplet);
        readyButton = new MTSvgButton(readyButtonSvgFile, pApplet);
        readyButtonDone = new MTSvgButton(readyButtonDoneSvgFile, pApplet);
        textAreaBackground = new MTRectangle(200, AbstractKeyboard.KEYBOARD_HEIGHT, pApplet);
        textAreaBackground.setFillColor(MTColor.WHITE);
        textAreaBackground.setNoStroke(false);
        textAreaBackground.setPickable(false);
        textAreaBackground.removeAllGestureEventListeners();
        textAreaBackground.unregisterAllInputProcessors();
        textArea = new MTTextArea(pApplet);
        textArea.setEnableCaret(true);
        textArea.setPickable(false);

        sendButton = new MTRectangle(30, 30, pApplet);
        sendButton.setFillColor(MTColor.WHITE);
        sendButton.removeAllGestureEventListeners();
        sendButton.unregisterAllInputProcessors();
        
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

    public MTRectangle getTextAreaBackground() {
        return textAreaBackground;
    }

    public MTTextArea getTextArea() {
        return textArea;
    }

    public MTRectangle getSendButton() {
        return sendButton;
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
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent() - 100, helpButton.getHeightXYRelativeToParent() - 30));
        problemButton.setPositionRelativeToParent(new Vector3D(-problemButton.getWidthXYRelativeToParent() - 100, keyboard.getHeight() - problemButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + closeButton.getWidthXYRelativeToParent() - 100, closeButton.getHeightXYRelativeToParent() - 30));
        readyButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + readyButton.getWidthXYRelativeToParent() - 100, keyboard.getHeight() - readyButton.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + readyButtonDone.getWidthXYRelativeToParent() - 100, keyboard.getHeight() - readyButtonDone.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setVisible(false);
        keyboard.setVisible(false);
        
        keyboard.addChild(textAreaBackground);
        textAreaBackground.setPositionRelativeToParent(new Vector3D(keyboard.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2, keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT)/2));
        textAreaBackground.addChild(textArea);
        textArea.setPositionRelativeToParent(new Vector3D(textArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, textArea.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));
        
        keyboard.addTextInputListener(textArea);   
        
        keyboard.addChild(sendButton);
        sendButton.setPositionRelativeToParent(new Vector3D(-100, 0));
    }
}

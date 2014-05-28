package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class StartUserWorkplace extends MTRectangle {

    private static final String helpButtonSvgFile = "data/helpButton.svg";
    private static final String settingsButtonSvgFile = "data/settingsButton.svg";
    private static final String startButtonSvgFile = "data/startButton.svg";
    private static final float buttonScaleFactor = 1.4f;

    private final AbstractKeyboard keyboard;
    private final MTSvgButton helpButton;
    private final MTSvgButton settingsButton;
    private final MTSvgButton startButton;

    public StartUserWorkplace(PApplet pApplet) {
        super(pApplet, 916, AbstractKeyboard.KEYBOARD_HEIGHT);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        keyboard = new Keyboard(pApplet);
        helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        settingsButton = new MTSvgButton(pApplet, settingsButtonSvgFile);
        startButton = new MTSvgButton(pApplet, startButtonSvgFile);
        
        positionAllComponents();
    }

    public AbstractKeyboard getKeyboard() {
        return keyboard;
    }

    public MTSvgButton getHelpButton() {
        return helpButton;
    }

    public MTSvgButton getSettingsButton() {
        return settingsButton;
    }

    public MTSvgButton getStartButton() {
        return startButton;
    }
    
    private void positionAllComponents() {
        addChild(keyboard);
        keyboard.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));
        keyboard.addChild(helpButton);
        keyboard.addChild(settingsButton);
        keyboard.addChild(startButton);
        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        settingsButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        startButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), helpButton.getHeightXYRelativeToParent() - 30));
        settingsButton.setPositionRelativeToParent(new Vector3D(-settingsButton.getWidthXYRelativeToParent(), keyboard.getHeight() - settingsButton.getHeightXYRelativeToParent() + 30));
        startButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + startButton.getWidthXYRelativeToParent(), keyboard.getHeight() - startButton.getHeightXYRelativeToParent() + 30));
    }
}

package de.fh_koeln.sgmci.mtwd.customelements.workspace;

import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import de.fh_koeln.sgmci.mtwd.customelements.Keyboard;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class StartUserWorkspace extends AbstractWorkspace {

    private final AbstractKeyboard keyboard;
    private final MTSvgButton helpButton;
    private final MTSvgButton settingsButton;
    private final MTSvgButton startButton;

    public StartUserWorkspace(PApplet pApplet) {
        super(pApplet, 916, 200, false);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        this.keyboard = new Keyboard(pApplet);
        this.helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        this.settingsButton = new MTSvgButton(pApplet, settingsButtonSvgFile);
        this.startButton = new MTSvgButton(pApplet, startButtonSvgFile);

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
        workspace.addChild(keyboard);
        keyboard.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        keyboard.addChild(closeButton);
        keyboard.addChild(helpButton);
        keyboard.addChild(settingsButton);
        keyboard.addChild(startButton);
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        settingsButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        startButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXYRelativeToParent() / 2, this.getHeightXYRelativeToParent() / 2));
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), helpButton.getHeightXYRelativeToParent() - 30));
        settingsButton.setPositionRelativeToParent(new Vector3D(-settingsButton.getWidthXYRelativeToParent(), keyboard.getHeight() - settingsButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + closeButton.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));
        startButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + startButton.getWidthXYRelativeToParent(), keyboard.getHeight() - startButton.getHeightXYRelativeToParent() + 30));
    }
}

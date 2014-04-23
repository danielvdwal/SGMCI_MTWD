package de.fh_koeln.sgmci.mtwd.customelements;

import de.fh_koeln.sgmci.mtwd.TestScene;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.math.Vector3D;

/**
 *
 * @author danielvanderwal
 */
public class KeyboardTest extends MTApplication {

    private static Keyboard KEYBOARD;

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        KEYBOARD = new Keyboard(this);
        TestScene testScene = new TestScene(this, "Test Scene");
        testScene.getCanvas().addChild(KEYBOARD);

        KEYBOARD.setPositionGlobal(new Vector3D(512, 360));

        final MTTextArea currentTextArea = new MTTextArea(this);
        currentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        currentTextArea.setEnableCaret(true);
        KEYBOARD.addChild(currentTextArea);
        currentTextArea.setPositionRelativeToParent(new Vector3D(40, -currentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        KEYBOARD.addTextInputListener(currentTextArea);
        
        this.addScene(testScene);
    }
}

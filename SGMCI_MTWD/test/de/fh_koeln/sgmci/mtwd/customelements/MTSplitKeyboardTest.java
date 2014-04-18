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
public class MTSplitKeyboardTest extends MTApplication {

    private static MTSplitKeyboard MT_SPLIT_KEYBOARD;

    public static void main(String[] args) {
        initialize();
    }

    @Override
    public void startUp() {
        MT_SPLIT_KEYBOARD = new MTSplitKeyboard(this);
        TestScene testScene = new TestScene(this, "Test Scene");
        testScene.getCanvas().addChild(MT_SPLIT_KEYBOARD);
        
        MT_SPLIT_KEYBOARD.setPositionGlobal(new Vector3D(512, 360));

        final MTTextArea currentTextArea = new MTTextArea(this);
        currentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        currentTextArea.setEnableCaret(true);
        MT_SPLIT_KEYBOARD.getLeftKeyboard().addChild(currentTextArea);
        currentTextArea.setPositionRelativeToParent(new Vector3D(40, -currentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        MT_SPLIT_KEYBOARD.addTextInputListener(currentTextArea);


        this.addScene(testScene);
    }
}

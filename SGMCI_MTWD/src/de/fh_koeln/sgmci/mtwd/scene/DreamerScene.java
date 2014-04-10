package de.fh_koeln.sgmci.mtwd.scene;

import java.awt.event.KeyEvent;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;
import org.mt4j.input.gestureAction.DefaultPanAction;
import org.mt4j.input.gestureAction.DefaultZoomAction;
import org.mt4j.input.inputProcessors.componentProcessors.lassoProcessor.LassoProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

/**
 *
 * @author danielvanderwal
 */
public class DreamerScene extends AbstractScene implements IScene {

    private final MTApplication mtApp;
    private LassoProcessor lassoProcessor;

    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.mtApp = mtApp;

        addEventListeners();

        createComponents();
    }

    private void addEventListeners() {
        this.getCanvas().registerInputProcessor(
                new PanProcessorTwoFingers(mtApp));
        this.getCanvas().addGestureListener(
                PanProcessorTwoFingers.class, new DefaultPanAction());

        this.getCanvas().registerInputProcessor(
                new ZoomProcessor(mtApp));
        this.getCanvas().addGestureListener(
                ZoomProcessor.class, new DefaultZoomAction());
    }

    private void createComponents() {
        for (int i = 0; i < 4; i++) {
            final MTKeyboard keyboard = new MTKeyboard(mtApp);
            keyboard.setFillColor(new MTColor(30, 30, 30, 210));
            keyboard.setStrokeColor(new MTColor(0, 0, 0, 255));

            final MTTextArea t = new MTTextArea(mtApp, FontManager.getInstance().
                    createFont(mtApp, "arial.ttf", 50, new MTColor(0, 0, 0, 255),
                    new MTColor(0, 0, 0, 255)));
            t.setExpandDirection(MTTextArea.ExpandDirection.UP);
            t.setStrokeColor(new MTColor(0, 0, 0, 255));
            t.setFillColor(new MTColor(205, 200, 177, 255));
            t.unregisterAllInputProcessors();
            t.setEnableCaret(true);
            t.snapToKeyboard(keyboard);
            keyboard.addTextInputListener(t);

            keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2.0f,
                    mtApp.height / 2.0f, 0));

            this.getCanvas().addChild(keyboard);
        }
    }

    @Override
    public void init() {
        mtApp.registerKeyEvent(this);
    }

    @Override
    public void shutDown() {
        mtApp.unregisterKeyEvent(this);
    }

    public void keyEvent(KeyEvent e) {
        int evtID = e.getID();
        if (evtID != KeyEvent.KEY_PRESSED) {
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                System.out.println("FPS: " + mtApp.frameRate);
                break;
            default:
                break;
        }
    }
}

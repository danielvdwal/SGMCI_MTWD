package de.fh_koeln.sgmci.mtwd.scene;

import java.awt.event.KeyEvent;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;
import org.mt4j.input.gestureAction.DefaultPanAction;
import org.mt4j.input.gestureAction.DefaultZoomAction;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
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

    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.mtApp = mtApp;

        addEventListeners();

        createComponents();
    }

    private void addEventListeners() {
        this.getCanvas().registerInputProcessor(new PanProcessorTwoFingers(
                mtApp));
        this.getCanvas().registerInputProcessor(new ZoomProcessor(mtApp));

        this.getCanvas().addGestureListener(ZoomProcessor.class,
                new DefaultZoomAction());
        this.getCanvas().addGestureListener(PanProcessorTwoFingers.class,
                new DefaultPanAction());
    }

    private void createComponents() {
        final IFont font = FontManager.getInstance().createFont(mtApp,
                "arial.ttf", 50);
        for (int i = 0; i < 4; i++) {
            final MTKeyboard keyboard = new MTKeyboard(mtApp);
            keyboard.setFillColor(new MTColor(30, 30, 30, 255));
            keyboard.setStrokeColor(new MTColor(0, 0, 0, 255));
            float width = keyboard.getWidthXY(TransformSpace.LOCAL);
            float height = keyboard.getHeightXY(TransformSpace.LOCAL);
            float ratio = (mtApp.getWidth() * 0.33f) / width;
            keyboard.scale(ratio, ratio, ratio, Vector3D.ZERO_VECTOR);
            
            width = width * ratio;
            height = height * ratio;

            final MTTextArea currentTextArea = new MTTextArea(mtApp, font);
            currentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
            currentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
            currentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
            currentTextArea.setEnableCaret(true);
            currentTextArea.snapToKeyboard(keyboard);
            keyboard.addTextInputListener(currentTextArea);

            final MTRectangle rectangle = new MTRectangle(0, 0, 30, 30, mtApp);
            rectangle.translate(new Vector3D(0, 15, 0));
            rectangle.registerInputProcessor(new TapProcessor(mtApp));
            rectangle.addGestureListener(TapProcessor.class,
                    new IGestureEventListener() {
                @Override
                public boolean processGestureEvent(MTGestureEvent ge) {
                    TapEvent te = (TapEvent) ge;
                    if (te.getId() == TapEvent.GESTURE_DETECTED) {
                        final MTTextArea newTextArea = new MTTextArea(mtApp, font);
                        newTextArea.setText(currentTextArea.getText());
                        getCanvas().addChild(newTextArea);

                        currentTextArea.setText("");
                    }
                    return false;
                }
            });
            keyboard.addChild(rectangle);

            switch (i) {
                case 0:
                    keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2,
                            mtApp.height - height / 2, 0));
                    break;
                case 1:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), 90);
                    keyboard.setPositionGlobal(new Vector3D(height/2, mtApp.height/2, 0));
                    break;
                case 2:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), 180);
                    keyboard.setPositionGlobal(new Vector3D(
                            mtApp.width / 2, height / 2, 0));
                    break;
                case 3:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), -90);
                    keyboard.setPositionGlobal(new Vector3D(mtApp.width - height / 2, mtApp.height/2, 0));
                    break;
            }

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

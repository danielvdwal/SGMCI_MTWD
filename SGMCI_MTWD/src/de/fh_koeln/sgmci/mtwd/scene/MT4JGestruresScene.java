package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.gestureAction.DefaultDragAction;
import org.mt4j.input.gestureAction.InertiaDragAction;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;

/**
 *
 * @author danielvanderwal
 */
public class MT4JGestruresScene extends AbstractScene {

    public MT4JGestruresScene(MTApplication mtApp, String name) {
        super(mtApp, name);

        final MTRectangle rect1 = new MTRectangle(0, 0, 100, 100, mtApp);
        final MTRectangle rect2 = new MTRectangle(150, 0, 100, 100, mtApp);
        final MTRectangle rect3 = new MTRectangle(300, 0, 100, 100, mtApp);

        rect1.unregisterAllInputProcessors();
        rect1.removeAllGestureEventListeners();
        rect2.unregisterAllInputProcessors();
        rect2.removeAllGestureEventListeners();
        rect3.unregisterAllInputProcessors();
        rect3.removeAllGestureEventListeners();

        rect1.registerInputProcessor(new DragProcessor(mtApp));
        rect1.registerInputProcessor(new TapProcessor(mtApp));

        rect2.registerInputProcessor(new DragProcessor(mtApp));

        rect3.registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        
        rect1.addGestureListener(DragProcessor.class, new DefaultDragAction());
        rect1.addGestureListener(TapProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapEvent te = (TapEvent) ge;
                if (te.getId() == TapEvent.GESTURE_DETECTED) {
                    float r = (float) (Math.random() * 255);
                    float g = (float) (Math.random() * 255);
                    float b = (float) (Math.random() * 255);
                    rect1.setFillColor(new MTColor(r, g, b));
                }
                return false;
            }
        });

        rect2.addGestureListener(DragProcessor.class, new InertiaDragAction(200, .95f, 17));

        rect3.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, rect3));
        rect3.addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapAndHoldEvent te = (TapAndHoldEvent)ge;
                if(te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                    MTRectangle rect = (MTRectangle)ge.getTargetComponent();
                    rect.setFillColor(MTColor.randomColor());
                }
                return false;
            }
        });

        this.getCanvas().addChild(rect1);
        this.getCanvas().addChild(rect2);
        this.getCanvas().addChild(rect3);
    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }
}

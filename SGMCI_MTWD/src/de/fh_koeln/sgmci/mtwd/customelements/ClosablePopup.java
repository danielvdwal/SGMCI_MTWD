package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class ClosablePopup extends Popup {
    
    private final PApplet pApplet;
    private final MTSvgButton closeButton;
    
    public ClosablePopup(PApplet pApplet) {
        super(pApplet);
        this.pApplet = pApplet;
        closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        
        positionAllComponents();
        addEventListeners();
    }
    
    @Override
    public void setText(String text) {
        super.setText(text);
        closeButton.setPositionRelativeToParent(new Vector3D(textArea.getWidthXY(TransformSpace.LOCAL), 0));
    }
    
    private void positionAllComponents() {
        textArea.addChild(closeButton);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.setPositionRelativeToParent(new Vector3D(textArea.getWidthXY(TransformSpace.LOCAL), 0));
    }
    
    private void addEventListeners() {
        closeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
            
            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if(mtge.getId()== TapEvent.GESTURE_ENDED) {
                    setVisible(false);
                }
                return false;
            }
        });
    }
}

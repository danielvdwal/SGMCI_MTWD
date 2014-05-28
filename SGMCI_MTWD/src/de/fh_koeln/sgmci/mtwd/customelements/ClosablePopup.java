package de.fh_koeln.sgmci.mtwd.customelements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class ClosablePopup extends Popup {
    
    private final MTSvgButton closeButton;
    
    public ClosablePopup(PApplet pApplet) {
        super(pApplet);
        closeButton = new MTSvgButton(closeButtonSvgFile, pApplet);
        
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
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getID() == TapEvent.BUTTON_CLICKED) {
                    setVisible(false);
                }
            }
        });
    }
}

package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class Popup extends MTRectangle {

    protected static final String closeButtonSvgFile = "data/closeButton.svg";
    protected static final float buttonScaleFactor = 1.4f;
    
    protected final MTTextArea textArea;
    
    public Popup(PApplet pApplet) {
        super(100, 100, pApplet);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setVisible(false);
        textArea = new MTTextArea(pApplet, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));
        
        positionAllComponents();
    }
    
    public float getWidth() {
        return textArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT);
    }
    
    public float getHeight() {
        return textArea.getHeightXY(TransformSpace.RELATIVE_TO_PARENT);
    }
    
    public void setText(String text) {
        textArea.setText(text);
        textArea.setPositionRelativeToParent(new Vector3D(getWidthXY(TransformSpace.LOCAL)/2, getHeightXY(TransformSpace.LOCAL)/2));
    }
    
    private void positionAllComponents() {
        addChild(textArea);
        
        textArea.setFillColor(MTColor.WHITE);
        textArea.setStrokeColor(MTColor.BLACK);
        textArea.setInnerPadding(30);
        textArea.setPositionRelativeToParent(new Vector3D(getWidthXY(TransformSpace.LOCAL)/2, getHeightXY(TransformSpace.LOCAL)/2));
    }
}

package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTSlider;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class Settings extends MTRectangle {

    protected static final String closeButtonSvgFile = "data/closeButton.svg";
    protected static final float buttonScaleFactor = 1.4f;

    private final MTSvgButton closeButton;
    private final MTTextField sliderLabel;
    private final MTTextField sliderMinLabel;
    private final MTTextField sliderMaxLabel;
    private final MTSlider slider;
    
    public Settings(PApplet pApplet) {
        super(pApplet, 500, 150);
        setFillColor(MTColor.SILVER);
        setStrokeColor(MTColor.BLACK);
        this.setVisible(false);
        closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        sliderLabel = new MTTextField(pApplet, 20, 30, 440, 35, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));
        sliderMinLabel = new MTTextField(pApplet, 40, 110, 35, 35, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));
        sliderMaxLabel = new MTTextField(pApplet, 460, 110, 35, 35, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));
        sliderLabel.setText("Dislike-Ratio:");
        sliderMinLabel.setText("0%");
        sliderMaxLabel.setText("100%");
        sliderLabel.setNoFill(true);
        sliderLabel.setNoStroke(true);
        sliderMinLabel.setNoFill(true);
        sliderMinLabel.setNoStroke(true);
        sliderMaxLabel.setNoFill(true);
        sliderMaxLabel.setNoStroke(true);
        sliderLabel.unregisterAllInputProcessors();
        sliderLabel.removeAllGestureEventListeners();
        sliderMinLabel.unregisterAllInputProcessors();
        sliderMinLabel.removeAllGestureEventListeners();
        sliderMaxLabel.unregisterAllInputProcessors();
        sliderMaxLabel.removeAllGestureEventListeners();
        slider = new MTSlider(pApplet, 20, 75, 460, 35, 0, 1);

        positionAllComponents();
        addEventListeners();
    }

    public float getSliderValue() {
        return slider.getValue();
    }

    public void setSliderValue(float value) {
        slider.setValue(value);
    }
    
    public MTSvgButton getCloseButton() {
        return closeButton;
    }

    private void positionAllComponents() {
        addChild(closeButton);
        addChild(sliderLabel);
        addChild(sliderMinLabel);
        addChild(sliderMaxLabel);
        addChild(slider);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL), 0));
    }

    private void addEventListeners() {
        closeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                    setVisible(false);
                }
                return false;
            }
        });
    }

}

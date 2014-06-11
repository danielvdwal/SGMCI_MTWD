package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractComponentProcessor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class Cloud extends MTSvg {

    private static final String cloudSvgFile = "data/cloud.svg";

    private final MTTextArea textArea;

    public Cloud(PApplet pApplet, boolean textInput) {
        super(pApplet, cloudSvgFile);
        textArea = new MTTextArea(pApplet, 0, 0, 120, 120, FontManager.getInstance().createFont(pApplet, "arial.ttf", 18));
        textArea.setNoFill(true);
        textArea.setNoStroke(true);

        positionAllComponents();
    }

    public MTTextArea getTextArea() {
        return textArea;
    }

    public void setTextAreaText(String text) {
        textArea.setText(text);
    }

    @Override
    public void registerInputProcessor(AbstractComponentProcessor inputProcessor) {
        super.registerInputProcessor(inputProcessor);
        if (textArea != null) {
            textArea.registerInputProcessor(inputProcessor);
        }
    }

    @Override
    public void unregisterAllInputProcessors() {
        super.unregisterAllInputProcessors();
        textArea.unregisterAllInputProcessors();
    }

    @Override
    public void addGestureListener(Class<? extends IInputProcessor> gestureEvtSender, IGestureEventListener listener) {
        super.addGestureListener(gestureEvtSender, listener);
        if (textArea != null) {
            textArea.addGestureListener(gestureEvtSender, listener);
        }
    }

    @Override
    public void removeAllGestureEventListeners() {
        super.removeAllGestureEventListeners();
        textArea.removeAllGestureEventListeners();
    }

    private void positionAllComponents() {
        addChild(textArea);
        textArea.setPositionRelativeToParent(new Vector3D(getWidthXYGlobal() / 2, getHeightXYGlobal() / 2));
    }
}

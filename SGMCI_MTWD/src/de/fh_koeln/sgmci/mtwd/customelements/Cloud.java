package de.fh_koeln.sgmci.mtwd.customelements;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final boolean textInput;

    private TextAreaPositionUpdateThread thread;

    public Cloud(PApplet pApplet, boolean textInput) {
        super(pApplet, cloudSvgFile);
        textArea = new MTTextArea(pApplet, FontManager.getInstance().createFont(pApplet, "arial.ttf", 18));
        textArea.setNoFill(true);
        textArea.setNoStroke(true);
        this.textInput = textInput;

        positionAllComponents();
    }

    public MTTextArea getTextArea() {
        return textArea;
    }

    public void setTextAreaText(String text) {
        textArea.setText(text);
        textArea.setPositionRelativeToParent(new Vector3D(getWidthXYRelativeToParent() / 2, getHeightXYRelativeToParent() / 2));
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
        textArea.setPositionRelativeToParent(new Vector3D(getWidthXYRelativeToParent() / 2, getHeightXYRelativeToParent() / 2));

        if (textInput) {
            thread = new TextAreaPositionUpdateThread(textArea, this);
            thread.start();
        }
    }

    static class TextAreaPositionUpdateThread extends Thread {

        private final MTTextArea textArea;
        private final MTSvg component;

        public TextAreaPositionUpdateThread(MTTextArea textArea, MTSvg component) {
            this.textArea = textArea;
            this.component = component;
        }

        @Override
        public void run() {
            while (true) {
                textArea.setPositionRelativeToParent(new Vector3D(component.getWidthXYRelativeToParent() / 2, component.getHeightXYRelativeToParent() / 2));
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DreamerUserWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.StartSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.ClosablePopup;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import de.fh_koeln.sgmci.mtwd.customelements.workspace.StartUserWorkspace;
import de.fh_koeln.sgmci.mtwd.exception.NoProblemTextException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Nadim Khan, Ramon Victor, Daniel van der Wal - Fachhochschule Koeln
 * Campus Gummersbach 2014
 * @version 0.3.0
 */
public class StartScene extends AbstractMTWDScene {

    private final StartUserWorkspace user1Workspace;
    private MTTextArea problemLabel;
    private MTTextArea problemInputField;
    private Popup errorMessagePopup;

    public StartScene(final MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new StartSceneController(this);
        this.user1Workspace = new StartUserWorkspace(mtApp);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/startBackground.png");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        this.getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, false));
    }

    @Override
    public void createComponents() {
        problemLabel = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, new MTColor(0, 0, 0, 255)));
        problemLabel.setNoFill(true);
        problemLabel.setNoStroke(true);
        problemLabel.setText("Bitte geben Sie Ihr Problem ein:");
        problemLabel.setPickable(false);
        problemLabel.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 - 150));

        problemInputField = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50));
        problemInputField.setNoFill(true);
        problemInputField.setEnableCaret(true);
        problemInputField.setPickable(false);

        user1Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user1Workspace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - user1Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20));

        errorMessagePopup = new ClosablePopup(mtApp);
        errorMessagePopup.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);

        getCanvas().addChild(problemLabel);
        getCanvas().addChild(problemInputField);
        getCanvas().addChild(user1Workspace);
        getCanvas().addChild(errorMessagePopup);

        TextAreaPositionUpdateThread problemTextAreaUpdateThread = new TextAreaPositionUpdateThread(problemInputField, problemLabel);
        problemTextAreaUpdateThread.start();
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        user1Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener());
        user1Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workspace.getCloseButton()));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseButtonListener());
        user1Workspace.getKeyboard().addTextInputListener(problemInputField);
        user1Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener());

        user1Workspace.getStartButton().addGestureListener(TapProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_STARTED:
                        try {
                            ((StartSceneController) controller).proceed(problemInputField.getText());
                        } catch (NoProblemTextException ex) {
                            errorMessagePopup.setText(ex.getMessage());
                            errorMessagePopup.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2));
                            errorMessagePopup.setVisible(true);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        user1Workspace.getSettingsButton().addGestureListener(TapProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        //wenn Button geklickt wurde
                        MTTextArea textArea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textArea.setText("Loesung!!!");
                        textArea.setNoStroke(true);
                        textArea.setNoFill(true);
                        textArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2));

                        mtApp.getCurrentScene().getCanvas().addChild(textArea);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void startScene() {
        updateScene();
    }

    @Override
    public void updateScene() {
        user1Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.USER1_ID));
    }

    static class TextAreaPositionUpdateThread extends Thread {

        private final MTTextArea textArea;
        private final MTRectangle component;

        public TextAreaPositionUpdateThread(MTTextArea textArea, MTRectangle component) {
            this.textArea = textArea;
            this.component = component;
        }

        @Override
        public void run() {
            while (true) {
                textArea.setPositionRelativeToOther(component, new Vector3D(component.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, 100 + textArea.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));
                try {
                    sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartScene.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public class AddWorkspaceButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_STARTED) {
                controller.setUserActive(AbstractMTWDSceneController.USER1_ID, true);
            }
            return false;
        }
    }

    public class CloseButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapAndHoldEvent te = (TapAndHoldEvent) ge;
            if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                if (te.isHoldComplete()) {
                    controller.setUserActive(AbstractMTWDSceneController.USER1_ID, false);
                }
            }
            return false;
        }
    }

    private class HelpButtonListener implements IGestureEventListener {

        private final Popup helpPopup;

        public HelpButtonListener() {
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            switch (mtge.getId()) {
                case TapEvent.GESTURE_STARTED:
                    user1Workspace.addChild(helpPopup);
                    helpPopup.setText(((StartSceneController) controller).getHelpText());
                    helpPopup.setPositionRelativeToParent(new Vector3D(user1Workspace.getWidthXY(TransformSpace.LOCAL) / 2, -helpPopup.getHeight() / 2 - 10));
                    helpPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    helpPopup.setVisible(false);
                    user1Workspace.removeChild(helpPopup);
                default:
                    break;
            }
            return false;
        }
    }
}

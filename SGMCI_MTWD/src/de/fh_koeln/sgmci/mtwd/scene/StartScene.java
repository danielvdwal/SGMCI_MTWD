package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.StartSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.StartUserWorkplace;
import de.fh_koeln.sgmci.mtwd.exception.NoProblemTextException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Nadim Khan, Ramon Victor, Daniel van der Wal - Fachhochschule Koeln
 * Campus Gummersbach 2014
 * @version 0.2.0
 */
public class StartScene extends AbstractMTWDScene {

    private final StartUserWorkplace user1Workplace;
    private MTTextArea problemLabel;
    private MTTextArea problemInputField;
    private MTTextArea errorMessageTextArea;

    public StartScene(final MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new StartSceneController(this);
        this.user1Workplace = new StartUserWorkplace(mtApp);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/startBackground.png");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        this.getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {
        problemLabel = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, new MTColor(0, 0, 0, 255), new MTColor(255, 255, 255, 255)));
        problemLabel.setNoFill(true);
        problemLabel.setNoStroke(true);
        problemLabel.setText("Bitte geben Sie Ihr Problem ein:");
        problemLabel.setPickable(false);
        problemLabel.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2 - 150));

        problemInputField = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50));
        problemInputField.setNoFill(true);
        problemInputField.setEnableCaret(true);
        problemInputField.setPickable(false);

        user1Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user1Workplace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - user1Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20));

        errorMessageTextArea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLACK, MTColor.WHITE));
        errorMessageTextArea.setFillColor(MTColor.WHITE);
        errorMessageTextArea.setStrokeColor(MTColor.BLACK);
        errorMessageTextArea.setVisible(false);
        
        this.getCanvas().addChild(problemLabel);
        this.getCanvas().addChild(problemInputField);
        this.getCanvas().addChild(user1Workplace);
        this.getCanvas().addChild(errorMessageTextArea);

        TextAreaPositionUpdateThread problemTextAreaUpdateThread = new TextAreaPositionUpdateThread(problemInputField, problemLabel);
        problemTextAreaUpdateThread.start();
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        user1Workplace.getKeyboard().addTextInputListener(problemInputField);
        
        user1Workplace.getHelpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        final MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textarea.setText("Problem?");
                        textarea.setNoStroke(true);
                        textarea.setNoFill(true);
                        textarea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

                        final MTTextArea helpPop = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 25, MTColor.BLUE, MTColor.BLUE));
                        helpPop.setText("Such bei Google nach Hilfe -.-");
                        helpPop.setPositionRelativeToOther(user1Workplace.getHelpButton(), new Vector3D(helpPop.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, -300));
                        helpPop.setPickable(false);
                        getCanvas().addChild(helpPop);

                        mtApp.getCurrentScene().getCanvas().addChild(textarea);
                        break;
                    default:
                        break;
                }
            }
        });

        user1Workplace.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        try {
                            ((StartSceneController)controller).proceed(problemInputField.getText());
                        } catch (NoProblemTextException ex) {
                            errorMessageTextArea.setText(ex.getMessage());
                            errorMessageTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));
                            errorMessageTextArea.setVisible(true);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        user1Workplace.getSettingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //wenn Button geklickt wurde
                        MTTextArea textArea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textArea.setText("Loesung!!!");
                        textArea.setNoStroke(true);
                        textArea.setNoFill(true);
                        textArea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

                        mtApp.getCurrentScene().getCanvas().addChild(textArea);
                        break;
                    default:
                        break;
                }
            }
        });
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
}

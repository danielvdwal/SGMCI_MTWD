package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.StartSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import de.fh_koeln.sgmci.mtwd.customelements.Keyboard;
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
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
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

    private final StartSceneController controller;
    private MTTextArea problemLabel;
    private MTTextArea problemInputField;
    private AbstractKeyboard keyboard;
    private MTSvgButton helpButton;
    private MTSvgButton settingsButton;
    private MTSvgButton startButton;
    private MTTextArea errorMessageTextArea;

    public StartScene(final MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new StartSceneController(this);
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

        keyboard = new Keyboard(mtApp);
        keyboard.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        keyboard.setPositionRelativeToParent(new Vector3D(mtApp.width / 2, mtApp.height - keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        helpButton = new MTSvgButton("data/button_help.svg", mtApp);
        helpButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        helpButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2 - 60, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2));

        startButton = new MTSvgButton("data/button_start.svg", mtApp);
        startButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        startButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 + keyboard.getWidthXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2 + 120, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2));

        settingsButton = new MTSvgButton("data/button_settings.svg", mtApp);
        settingsButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        settingsButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2 - 180, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * keyboardScaleFactor / 2));

        errorMessageTextArea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLACK, MTColor.WHITE));
        errorMessageTextArea.setFillColor(MTColor.WHITE);
        errorMessageTextArea.setStrokeColor(MTColor.BLACK);
        errorMessageTextArea.setVisible(false);

        MTSvgButton button1 = new MTSvgButton("data/problemButton.svg", mtApp);
        MTSvgButton button2 = new MTSvgButton("data/readyButton.svg", mtApp);
        MTSvgButton button3 = new MTSvgButton("data/helpButton.svg", mtApp);
        MTSvgButton button4 = new MTSvgButton("data/plusButton.svg", mtApp);
        MTSvgButton button5 = new MTSvgButton("data/readyButtonDone.svg", mtApp);
        
        
        this.getCanvas().addChild(problemLabel);
        this.getCanvas().addChild(problemInputField);
        this.getCanvas().addChild(keyboard);
        this.getCanvas().addChild(helpButton);
        this.getCanvas().addChild(startButton);
        this.getCanvas().addChild(settingsButton);
        this.getCanvas().addChild(errorMessageTextArea);

        button1.setPositionGlobal(new Vector3D(100, 100, 0));
        button2.setPositionGlobal(new Vector3D(200, 200, 0));
        button3.setPositionGlobal(new Vector3D(300, 300, 0));
        button4.setPositionGlobal(new Vector3D(400, 400, 0));
        button5.setPositionGlobal(new Vector3D(500, 500, 0));
        
        this.getCanvas().addChild(button1);
        this.getCanvas().addChild(button2);
        this.getCanvas().addChild(button3);
        this.getCanvas().addChild(button4);
        this.getCanvas().addChild(button5);
        
        TextAreaPositionUpdateThread problemTextAreaUpdateThread = new TextAreaPositionUpdateThread(problemInputField, problemLabel);
        problemTextAreaUpdateThread.start();
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        keyboard.addTextInputListener(problemInputField);
        keyboard.removeAllGestureEventListeners();
        keyboard.unregisterAllInputProcessors();

        helpButton.addActionListener(new ActionListener() {
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
                        helpPop.setPositionRelativeToOther(helpButton, new Vector3D(helpPop.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, -300));
                        helpPop.setPickable(false);
                        getCanvas().addChild(helpPop);

                        final MTSvg svgTest = new MTSvg(mtApp, "data/Zeichnung.svg");
                        getCanvas().addChild(svgTest);

                        mtApp.getCurrentScene().getCanvas().addChild(textarea);
                        break;
                    default:
                        break;
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        try {
                            controller.proceed(problemInputField.getText());
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

        settingsButton.addActionListener(new ActionListener() {
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

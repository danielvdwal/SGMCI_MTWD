package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.StartSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.Keyboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.transition.BlendTransition;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;

/**
 *
 * @author Nadim Khan, Ramon Victor - Fachhochschule Koeln Campus Gummersbach
 * 2014
 *
 * Diese Klasse repraesentiert den Startbildschirm.
 *
 */
public class StartScene extends AbstractMTWDScene {

    private final StartSceneController controller;
    
    public StartScene(final MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new StartSceneController(this);
        
        //Zeigt die Touch-Stellen auf dem Bildschirm an.
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        this.getCanvas().setFrustumCulling(false);
        this.setClearColor(new MTColor(146, 150, 188, 255));

        //Erstellt das Texfeld sowie das zugehoerige Inputfeld.
        final MTTextArea textField = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50,
                new MTColor(255, 255, 255, 255),
                new MTColor(255, 255, 255, 255)));
        textField.setNoFill(true);
        textField.setNoStroke(true);
        textField.setText("Bitte geben Sie Ihr Problem ein:");

        final MTTextArea textInput = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50));
        textInput.setNoFill(true);

        //Stellt die korrekte Position des Inputfeldes sicher.
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    textInput.setPositionRelativeToOther(textField, new Vector3D(textField.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, 100 + textInput.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));
                    try {
                        sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StartScene.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();

        Keyboard keyboard = new Keyboard(mtApp);
        keyboard.addTextInputListener(textInput);
        keyboard.removeAllGestureEventListeners();
        keyboard.unregisterAllInputProcessors();
        
        float ratio = (mtApp.getWidth() * 0.5f) / keyboard.getWidthXY(TransformSpace.LOCAL);
        keyboard.scale(ratio, ratio, ratio, Vector3D.ZERO_VECTOR);


        //Fuegt die Komponenten dem Canvas hinzu.
        this.getCanvas().addChild(textField);
        this.getCanvas().addChild(keyboard);
        this.getCanvas().addChild(textInput);

        textField.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2 - 150));
        textInput.setEnableCaret(true);
        textInput.setPickable(false);
        textField.setPickable(false);
        keyboard.setPositionRelativeToParent(new Vector3D(mtApp.width / 2, mtApp.height - keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        final MTSvgButton helpButton = new MTSvgButton("data/button_help.svg", mtApp);
        final MTSvgButton startButton = new MTSvgButton("data/button_start.svg", mtApp);
        final MTSvgButton settingsButton = new MTSvgButton("data/button_settings.svg", mtApp);

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //wenn Button geklickt wurde
                        MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textarea.setText("Problem?");
                        textarea.setNoStroke(true);
                        textarea.setNoFill(true);
                        textarea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

                        MTTextArea helpPop = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 25, MTColor.BLUE, MTColor.BLUE));
                        helpPop.setText("Such bei Google nach Hilfe -.-");
                        helpPop.setPositionRelativeToOther(helpButton, new Vector3D(helpPop.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, -300));
                        helpPop.setPickable(false);
                        getCanvas().addChild(helpPop);

                        MTSvg svgTest = new MTSvg(mtApp, "data/Zeichnung.svg");
                        getCanvas().addChild(svgTest);


                        mtApp.getCurrentScene().getCanvas().addChild(textarea);
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
                        MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textarea.setText("Loesung!!!");
                        textarea.setNoStroke(true);
                        textarea.setNoFill(true);
                        textarea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

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
                        controller.proceed(textInput.getText());
                        break;
                    default:
                        break;
                }
            }
        });

        helpButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        settingsButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        startButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        
        helpButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 - 60, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));
        settingsButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 - 180, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));        
        startButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 + keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 + 120, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));
        

        this.getCanvas().addChild(helpButton);
        this.getCanvas().addChild(startButton);
        this.getCanvas().addChild(settingsButton);

        //Set a scene transition for our StartScene- Blend transition only available using opengl supporting the FBO extenstion
        //BlendTransition, da es im gegensatz zu slide aus allen Blickwinkeln gut aussieht ;-)
        if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)) {
            this.setTransition(new BlendTransition(mtApp, 1200));
        } else {
            this.setTransition(new FadeTransition(mtApp));
        }
    }
}

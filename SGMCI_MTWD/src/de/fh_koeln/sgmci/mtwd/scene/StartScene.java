package de.fh_koeln.sgmci.mtwd.scene;

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
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.Iscene;
import org.mt4j.sceneManagement.transition.BlendTransition;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;

import processing.core.PImage;

/**
 *
 * @author Nadim Khan, Ramon Victor - Fachhochschule Koeln Campus Gummersbach
 * 2014
 *
 * Diese Klasse repraesentiert den Startbildschirm.
 *
 */
public class StartScene extends AbstractScene implements IScene {

    private MTApplication mtApp;
    private Iscene dreamScene;

    public StartScene(final MTApplication mtApp, String name) {
        super(mtApp, name);
        this.mtApp = mtApp;

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

        //Fuegt die Komponenten dem Canvas hinzu.
        this.getCanvas().addChild(textField);
        this.getCanvas().addChild(keyboard);
        this.getCanvas().addChild(textInput);

        textField.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2 - 150));
        textInput.setEnableCaret(true);
        textInput.setPickable(false);
        textField.setPickable(false);
        keyboard.setPositionRelativeToParent(new Vector3D(mtApp.width / 2, mtApp.height - keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        PImage helpImage = null;
        PImage startImage = null;
        PImage settingsImage = null;
        try {
            helpImage = mtApp.loadImage("data/helpButton.png");
            startImage = mtApp.loadImage("data/startButton.png");
            settingsImage = mtApp.loadImage("data/settingsButton.png");
        } catch (Exception e) {
            System.out.println("Error: Bilder konnte nicht geladen werden!");;
        }

        final MTImageButton helpButton = new MTImageButton(helpImage, mtApp);
        helpButton.setNoStroke(true);
        helpButton.setDrawSmooth(true);

        if (MT4jSettings.getInstance().isOpenGlMode()) {
            helpButton.setUseDirectGL(true);
        }

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

        helpButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 6, mtApp.getHeight() - mtApp.getHeight() / 14));
        helpButton.setSizeXYGlobal(mtApp.getWidth() / 16, mtApp.getHeight() / 9);

        this.getCanvas().addChild(helpButton);


        MTImageButton settingsButton = new MTImageButton(settingsImage, mtApp);
        settingsButton.setNoStroke(true);
        settingsButton.setDrawSmooth(true);

        if (MT4jSettings.getInstance().isOpenGlMode()) {
            settingsButton.setUseDirectGL(true);
        }

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

        settingsButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 12, mtApp.getHeight() - mtApp.getHeight() / 14));
        settingsButton.setSizeXYGlobal(mtApp.getWidth() / 16, mtApp.getHeight() / 9);

        this.getCanvas().addChild(settingsButton);


        MTImageButton startButton = new MTImageButton(startImage, mtApp);
        startButton.setNoStroke(true);
        startButton.setDrawSmooth(true);

        if (MT4jSettings.getInstance().isOpenGlMode()) {
            startButton.setUseDirectGL(true);
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //wenn Button geklickt wurde
                        //Save the current scene on the scene stack before changing
                        mtApp.pushScene();
                        if (dreamScene == null) {
                            dreamScene = new DreamScene(mtApp, "Scene 2");
                            //Add the scene to the mt application
                            mtApp.addScene(dreamScene);
                        }
                        //Do the scene change
                        mtApp.changeScene(dreamScene);
                        break;
                    default:
                        break;
                }
            }
        });

        startButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 1.2f, mtApp.getHeight() - mtApp.getHeight() / 14));
        startButton.setSizeXYGlobal(mtApp.getWidth() / 16, mtApp.getHeight() / 9);

        this.getCanvas().addChild(startButton);

        //Set a scene transition for our StartScene- Blend transition only available using opengl supporting the FBO extenstion
        //BlendTransition, da es im gegensatz zu slide aus allen Blickwinkeln gut aussieht ;-)
        if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)) {
            this.setTransition(new BlendTransition(mtApp, 1200));
        } else {
            this.setTransition(new FadeTransition(mtApp));
        }

    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }
}

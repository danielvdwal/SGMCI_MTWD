package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.EvaluationSceneController;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth
 * @version 0.3.0
 */
public class EvaluationScene extends AbstractMTWDScene {

    private static final String FOUND_IDEAS_TEXT = "Ideen gefunden: %d";
    private static final String ALL_IDEAS_TEXT = "Alle Ideen: %d";
    
    private IScene startScene;
    private IScene dreamerScene;

    private MTTextArea headlineTextArea;
    private MTTextArea problemTextArea;
    private MTTextArea foundIdeasTextArea;
    private MTTextArea allIdeasTextArea;
    private MTTextArea moduleTextArea;
    private MTTextArea profTextArea;

    private MTSvgButton continueButton;
    private MTSvgButton saveButton;
    private MTSvgButton restartButton;
    private MTSvgButton endButton;

    public EvaluationScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new EvaluationSceneController(this);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_black.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, false));
    }

    @Override
    public void createComponents() {

        final IFont headlineFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 24, MTColor.WHITE);
        final IFont generalFond = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18, MTColor.WHITE);

        headlineTextArea = new MTTextArea(mtApp, headlineFont);
        headlineTextArea.setNoFill(true);
        headlineTextArea.setNoStroke(true);
        headlineTextArea.setPickable(false);
        headlineTextArea.setText("Ende");

        problemTextArea = new MTTextArea(mtApp, generalFond);
        problemTextArea.setNoFill(true);
        problemTextArea.setNoStroke(true);
        problemTextArea.setPickable(false);

        //The description of the technique
        foundIdeasTextArea = new MTTextArea(mtApp, generalFond);
        foundIdeasTextArea.setNoFill(true);
        foundIdeasTextArea.setNoStroke(true);
        foundIdeasTextArea.setPickable(false);
        foundIdeasTextArea.setText(FOUND_IDEAS_TEXT);

        allIdeasTextArea = new MTTextArea(mtApp, generalFond);
        allIdeasTextArea.setNoFill(true);
        allIdeasTextArea.setNoStroke(true);
        allIdeasTextArea.setPickable(false);
        allIdeasTextArea.setText(ALL_IDEAS_TEXT);

        moduleTextArea = new MTTextArea(mtApp, generalFond);
        moduleTextArea.setNoFill(true);
        moduleTextArea.setNoStroke(true);
        moduleTextArea.setPickable(false);
        moduleTextArea.setText("spezielle Gebiete der MCI / SS14");

        //Prof Area
        profTextArea = new MTTextArea(mtApp, generalFond);
        profTextArea.setNoFill(true);
        profTextArea.setNoStroke(true);
        profTextArea.setPickable(false);
        profTextArea.setText("Prof. Dr. Heiner Klocke");

        continueButton = new MTSvgButton(mtApp, "data/replayButton.svg");
        saveButton = new MTSvgButton(mtApp, "data/saveButton_light.svg");
        restartButton = new MTSvgButton(mtApp, "data/restartButton_light.svg");
        endButton = new MTSvgButton(mtApp, "data/endButton_light.svg");

        getCanvas().addChild(headlineTextArea);
        getCanvas().addChild(problemTextArea);
        getCanvas().addChild(foundIdeasTextArea);
        getCanvas().addChild(allIdeasTextArea);
        getCanvas().addChild(moduleTextArea);
        getCanvas().addChild(profTextArea);
        getCanvas().addChild(continueButton);
        getCanvas().addChild(saveButton);
        getCanvas().addChild(restartButton);
        getCanvas().addChild(endButton);

        headlineTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, 200, 0));
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        foundIdeasTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 + 100, 0));
        allIdeasTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 + 130, 0));
        moduleTextArea.setPositionGlobal(new Vector3D(moduleTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height - 20, 0));
        profTextArea.setPositionGlobal(new Vector3D(mtApp.width - profTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height - 20, 0));
        continueButton.setPositionGlobal(new Vector3D(mtApp.width / 2 - 150, mtApp.height - 150, 0));
        saveButton.setPositionGlobal(new Vector3D(mtApp.width / 2 - 50, mtApp.height - 150, 0));
        restartButton.setPositionGlobal(new Vector3D(mtApp.width / 2 + 50, mtApp.height - 150, 0));
        endButton.setPositionGlobal(new Vector3D(mtApp.width / 2 + 150, mtApp.height - 150, 0));

    }

    @Override
    public void startScene() {
        problemTextArea.setText(controller.getCurrentProblemDescription());
        foundIdeasTextArea.setText(String.format(FOUND_IDEAS_TEXT, controller.getAllVisibleIdeasForCurrentProblem().size()));
        allIdeasTextArea.setText(String.format(ALL_IDEAS_TEXT, controller.getAllIdeasForCurrentProblem().size()));
        
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        foundIdeasTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 + 100, 0));
        allIdeasTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 + 130, 0));
    }
    
    public IScene getStartScene() {
        return startScene;
    }

    public void setStartScene(IScene startScene) {
        this.startScene = startScene;
    }

    public IScene getDreamerScene() {
        return dreamerScene;
    }

    public void setDreamerScene(IScene dreamerScene) {
        this.dreamerScene = dreamerScene;
    }

    @Override
    public void createEventListeners() {
        continueButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        setNextScene(dreamerScene);
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        restartButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        setNextScene(startScene);
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        saveButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        ((EvaluationSceneController) controller).saveResultsIntoXmlFile();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        endButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}

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

    private MTTextArea headlineTextArea;
    private MTTextArea foundTextArea;
    private MTTextArea moduleTextArea;
    private MTTextArea profTextArea;

    private MTSvgButton startButton;
    private MTSvgButton replayButton;

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

        //The description of the technique
        foundTextArea = new MTTextArea(mtApp, generalFond);
        foundTextArea.setNoFill(true);
        foundTextArea.setNoStroke(true);
        foundTextArea.setPickable(false);
        foundTextArea.setText("Ideen gefunden: 12");

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

        startButton = new MTSvgButton(mtApp, "data/startButton.svg");
        replayButton = new MTSvgButton(mtApp, "data/replayButton.svg");

        getCanvas().addChild(headlineTextArea);
        getCanvas().addChild(foundTextArea);
        getCanvas().addChild(moduleTextArea);
        getCanvas().addChild(profTextArea);
        getCanvas().addChild(startButton);
        getCanvas().addChild(replayButton);

        headlineTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, 250, 0));
        foundTextArea.setPositionGlobal(new Vector3D(mtApp.width / 6, 350, 0));
        moduleTextArea.setPositionGlobal(new Vector3D(moduleTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height - 20, 0));
        profTextArea.setPositionGlobal(new Vector3D(mtApp.width - profTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height - 20, 0));
        startButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150, 0));
        replayButton.setPositionGlobal(new Vector3D(mtApp.width / 2 - 150, mtApp.height - 150, 0));
    }

    @Override
    public void createEventListeners() {
        startButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        //controller.proceed(problemInputField.getText());
                        //((EvaluationSceneController)controller).saveResultsIntoXmlFile();
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}

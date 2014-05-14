package de.fh_koeln.sgmci.mtwd.scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth
 * @version 0.2.0
 */
public class EvaluationScene extends AbstractMTWDScene {

    private MTTextArea headlineTextArea;
    private MTTextArea foundTextArea;
    private MTTextArea moduleTextArea;
    private MTTextArea profTextArea;

    private MTSvgButton startButton;

    public EvaluationScene(MTApplication mtApp, String name) {
        super(mtApp, name);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_black.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {

        final IFont headlineFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 24, MTColor.WHITE, MTColor.WHITE);
        final IFont generalFond = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18, MTColor.WHITE, MTColor.WHITE);

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

        startButton = new MTSvgButton("data/button_start.svg", mtApp);
        startButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);

        getCanvas().addChild(headlineTextArea);
        getCanvas().addChild(foundTextArea);
        getCanvas().addChild(moduleTextArea);
        getCanvas().addChild(profTextArea);
        getCanvas().addChild(startButton);

        headlineTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, 250, 0));
        foundTextArea.setPositionGlobal(new Vector3D(mtApp.width / 6, 350, 0));
        moduleTextArea.setPositionGlobal(new Vector3D(moduleTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height - 20, 0));
        profTextArea.setPositionGlobal(new Vector3D(mtApp.width - profTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height - 20, 0));
        startButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150, 0));
    }

    @Override
    public void createEventListeners() {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //controller.proceed(problemInputField.getText());
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void updateScene() {
        //startButton.setVisible(true);
        //getCanvas().addChild(startButton);  
    }
}

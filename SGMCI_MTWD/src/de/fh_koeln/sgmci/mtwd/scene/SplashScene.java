package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.SplashSceneController;
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
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public class SplashScene extends AbstractMTWDScene {

    private MTTextArea headlineTextArea;
    private MTTextArea subheadlineTextArea;
    private MTTextArea descriptionTextArea;
    private MTTextArea personsTextArea;
    private MTTextArea moduleTextArea;
    private MTTextArea profTextArea;
    private MTSvgButton startButton;
    private MTTextArea loadingTextArea;

    public SplashScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.controller = new SplashSceneController(this);
        
        // remove transition
        this.setTransition(null);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/startBackground.png");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {

        final IFont headlineFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);
        final IFont subheadlineFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 24);
        final IFont descriptionFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);
        final IFont personsFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);

        headlineTextArea = new MTTextArea(mtApp, headlineFont);
        headlineTextArea.setNoFill(true);
        headlineTextArea.setNoStroke(true);
        headlineTextArea.setPickable(false);
        headlineTextArea.setText(SplashSceneController.APPLICATION_ABBREVIATION);

        subheadlineTextArea = new MTTextArea(mtApp, subheadlineFont);
        subheadlineTextArea.setNoFill(true);
        subheadlineTextArea.setNoStroke(true);
        subheadlineTextArea.setPickable(false);
        subheadlineTextArea.setText(SplashSceneController.APPLICATION_NAME);

        //The description of the technique
        descriptionTextArea = new MTTextArea(mtApp, descriptionFont);
        descriptionTextArea.setNoFill(true);
        descriptionTextArea.setNoStroke(true);
        descriptionTextArea.setPickable(false);
        descriptionTextArea.setText(SplashSceneController.TECHINQUE_DESCRIPTION);

        //Description of the Team-Members
        personsTextArea = new MTTextArea(mtApp, personsFont);
        personsTextArea.setNoFill(true);
        personsTextArea.setNoStroke(true);
        personsTextArea.setPickable(false);
        personsTextArea.setText(SplashSceneController.DEVELOPERS);

        //Prof Area
        moduleTextArea = new MTTextArea(mtApp, personsFont);
        moduleTextArea.setNoFill(true);
        moduleTextArea.setNoStroke(true);
        moduleTextArea.setPickable(false);
        moduleTextArea.setText(SplashSceneController.MODULE);

        //Prof Area
        profTextArea = new MTTextArea(mtApp, personsFont);
        profTextArea.setNoFill(true);
        profTextArea.setNoStroke(true);
        profTextArea.setPickable(false);
        profTextArea.setText(SplashSceneController.PROF);

        startButton = new MTSvgButton("data/startButton.svg", mtApp);

        loadingTextArea = new MTTextArea(mtApp, subheadlineFont);
        loadingTextArea.setNoFill(true);
        loadingTextArea.setNoStroke(true);
        loadingTextArea.setPickable(false);
        loadingTextArea.setText(SplashSceneController.LOADING_TEXT);
        loadingTextArea.setVisible(false);

        getCanvas().addChild(headlineTextArea);
        getCanvas().addChild(subheadlineTextArea);
        getCanvas().addChild(descriptionTextArea);
        getCanvas().addChild(personsTextArea);
        getCanvas().addChild(moduleTextArea);
        getCanvas().addChild(profTextArea);
        getCanvas().addChild(startButton);
        getCanvas().addChild(loadingTextArea);

        headlineTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 - 120, 0));
        subheadlineTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2 - 90, 0));
        descriptionTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2 - descriptionTextArea.getWidthXY(TransformSpace.LOCAL) / 2 - 20, mtApp.height / 2 - 50 + descriptionTextArea.getHeightXY(TransformSpace.LOCAL) / 2, 0));
        personsTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2 + personsTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height / 2 - 50 + personsTextArea.getHeightXY(TransformSpace.LOCAL) / 2, 0));
        moduleTextArea.setPositionGlobal(new Vector3D(moduleTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height - moduleTextArea.getHeightXY(TransformSpace.LOCAL) / 2  - 20, 0));
        profTextArea.setPositionGlobal(new Vector3D(mtApp.width - profTextArea.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height - profTextArea.getHeightXY(TransformSpace.LOCAL) / 2 - 20, 0));
        startButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150, 0));
        loadingTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 200, 0));
    }

    @Override
    public void createEventListeners() {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_DOWN:
                        loadingTextArea.setVisible(true);
                        startButton.setVisible(false);
                        break;
                    case TapEvent.BUTTON_CLICKED:
                        main.loadResources();
                        ((SplashSceneController)controller).proceed();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.RealistVotingSceneController;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

/**
 *
 * @author Nadim Khan, Ramon Victor
 * @version 0.2.0
 */
public class RealistVotingScene extends AbstractMTWDScene {

    private final RealistVotingSceneController controller;
    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private MTTextArea ideaUser1;
    private MTSvgButton leftButton, rightButton, likeButton, dislikeButton, continueButton;
    private int ideaIndex = 1;
    private List<Idea> allIdeas;

    public RealistVotingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new RealistVotingSceneController(this);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_wood.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, false));
    }

    @Override
    public void createEventListeners() {
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        if (ideaIndex != 0) {
                            --ideaIndex;
                            rightButton.setEnabled(true);
                            rightButton.setVisible(true);
                            ideaUser1.setText(allIdeas.get(ideaIndex).getDescription());
                            ideaUser1.setSizeLocal(200f, 150f);
                            ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150f, 0));
                            if (ideaIndex == 0) {
                                leftButton.setEnabled(false);
                                leftButton.setVisible(false);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        if (ideaIndex != allIdeas.size() - 1) {
                            ++ideaIndex;
                            leftButton.setEnabled(true);
                            leftButton.setVisible(true);
                            ideaUser1.setText(allIdeas.get(ideaIndex).getDescription());
                            ideaUser1.setSizeLocal(200f, 150f);
                            ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150f, 0));
                            if (ideaIndex == allIdeas.size() - 1) {
                                rightButton.setEnabled(false);
                                rightButton.setVisible(false);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void createComponents() {
        IFont problemFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);
        IFont ideaFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);

        problemTextArea = new MTTextArea(mtApp, problemFont);
        problemTextArea.setNoFill(true);
        problemTextArea.setNoStroke(true);
        problemTextArea.setPickable(false);

        problemTextAreaInverted = new MTTextArea(mtApp, problemFont);
        problemTextAreaInverted.setNoFill(true);
        problemTextAreaInverted.setNoStroke(true);
        problemTextAreaInverted.setPickable(false);
        problemTextAreaInverted.rotateZ(Vector3D.ZERO_VECTOR, 180);

        getCanvas().addChild(problemTextArea);
        getCanvas().addChild(problemTextAreaInverted);

        ideaUser1 = new MTTextArea(mtApp, ideaFont);
        ideaUser1.setPickable(false);

        leftButton = new MTSvgButton("data/arrowLeft.svg", mtApp);
        rightButton = new MTSvgButton("data/arrowRight.svg", mtApp);
        likeButton = new MTSvgButton("data/likeButton2.svg", mtApp);
        dislikeButton = new MTSvgButton("data/dislikeButton2.svg", mtApp);
        continueButton = new MTSvgButton("data/button_start.svg", mtApp);
        
        ideaUser1.addChild(rightButton);
        ideaUser1.addChild(leftButton);
        ideaUser1.addChild(likeButton);
        ideaUser1.addChild(dislikeButton);
        ideaUser1.addChild(continueButton);
        getCanvas().addChild(ideaUser1);
    }

    @Override
    public void startScene() {
        allIdeas = controller.getAllVisibleIdeasForCurrentProblem();
        
        ideaUser1.setText(allIdeas.get(ideaIndex).getDescription());
        ideaUser1.setSizeLocal(200f, 150f);
        ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150f, 0));
        
        leftButton.scale(0.5f, 0.5f, 0.5f, Vector3D.ZERO_VECTOR);
        leftButton.setPositionRelativeToParent(new Vector3D(-50, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));
        rightButton.scale(0.5f, 0.5f, 0.5f, Vector3D.ZERO_VECTOR);
        rightButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) + 50, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));
        likeButton.scale(1.5f, 1.5f, 1.5f, Vector3D.ZERO_VECTOR);
        likeButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) / 3, ideaUser1.getHeightXY(TransformSpace.LOCAL) + 25));
        dislikeButton.scale(1.5f, 1.5f, 1.5f, Vector3D.ZERO_VECTOR);
        dislikeButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) / 3 * 2, ideaUser1.getHeightXY(TransformSpace.LOCAL) + 25));
        continueButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        continueButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) + 250, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));
        
        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }
    
    @Override
    public void updateScene() {
    }
}

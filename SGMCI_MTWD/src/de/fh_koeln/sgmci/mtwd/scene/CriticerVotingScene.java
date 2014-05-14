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
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.transition.BlendTransition;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;
import processing.core.PImage;
import de.fh_koeln.sgmci.mtwd.controller.RealistVotingSceneController;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.model.Problem;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public class CriticerVotingScene extends AbstractMTWDScene {

    private final RealistVotingSceneController controller;
    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private MTTextArea ideaUser1, ideaUser2, ideaUser3, ideaUser4;
    private MTSvgButton leftButton, rightButton, likeButton, dislikeButton, continueButton;
    private int ideaIndex = 1;
    private Idea[] allIdeas;

    public CriticerVotingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new RealistVotingSceneController(this);

        // Set a scene transition for our StartScene.
        // Blend transition only available using opengl supporting the FBO extenstion.
        if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)) {
            this.setTransition(new BlendTransition(mtApp, 1200));
        } else {
            this.setTransition(new FadeTransition(mtApp));
        }
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_darkred.jpg");
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
                            ideaUser1.setText(allIdeas[ideaIndex].getDescription());
                            ideaUser1.setSizeLocal(300f, 250f);
                            ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
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
                        if (ideaIndex != allIdeas.length - 1) {
                            ++ideaIndex;
                            leftButton.setEnabled(true);
                            leftButton.setVisible(true);
                            ideaUser1.setText(allIdeas[ideaIndex].getDescription());
                            ideaUser1.setSizeLocal(300f, 250f);
                            ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
                            if (ideaIndex == allIdeas.length - 1) {
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

        Problem problem = new Problem("TestProblem", "Testbeschreibung");
        problem.addIdea("I1", "Idea1");
        problem.addIdea("I2", "Idea2");
        problem.addIdea("I3", "Idea3");
        problem.addIdea("I4", "Idea4");

        allIdeas = new Idea[problem.getAllIdeas().size()];
        int count = 0;
        for (Idea i : problem.getAllIdeas()) {
            allIdeas[count] = i;
            ++count;
        }

        ideaUser1 = new MTTextArea(mtApp, ideaFont);
        ideaUser1.setPickable(false);
        ideaUser1.setText(allIdeas[ideaIndex].getDescription());
        ideaUser1.setSizeLocal(300f, 250f);
        ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));

        leftButton = new MTSvgButton("data/arrowLeft.svg", mtApp);
        leftButton.setPositionRelativeToParent(new Vector3D(-100, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));;
        rightButton = new MTSvgButton("data/arrowRight.svg", mtApp);
        rightButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) + 100, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));
        likeButton = new MTSvgButton("data/likeButton2.svg", mtApp);
        likeButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) / 3, ideaUser1.getHeightXY(TransformSpace.LOCAL) + 25));
        dislikeButton = new MTSvgButton("data/dislikeButton2.svg", mtApp);
        dislikeButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) / 3 * 2, ideaUser1.getHeightXY(TransformSpace.LOCAL) + 25));
        continueButton = new MTSvgButton("data/button_start.svg", mtApp);
        continueButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        continueButton.setPositionRelativeToParent(new Vector3D(ideaUser1.getWidthXY(TransformSpace.LOCAL) + 250, ideaUser1.getHeightXY(TransformSpace.LOCAL) / 2));

        ideaUser1.addChild(rightButton);
        ideaUser1.addChild(leftButton);
        ideaUser1.addChild(likeButton);
        ideaUser1.addChild(dislikeButton);
        ideaUser1.addChild(continueButton);
        getCanvas().addChild(ideaUser1);
    }

    @Override
    public void updateScene() {
        //problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        //problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }

}

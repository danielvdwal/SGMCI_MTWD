package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.CriticerVotingSceneController;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.util.List;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.3.0
 */
public class CriticerVotingScene extends AbstractMTWDScene {

    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private MTTextArea ideaUser1;
    private MTSvgButton leftButton, rightButton, likeButton, dislikeButton, continueButton;
    private int ideaIndex = 0;
    private List<Idea> allIdeas;

    public CriticerVotingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new CriticerVotingSceneController(this);
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

        continueButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        leftButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
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
                return false;
            }
        });

        rightButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
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
                return false;
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

        leftButton = new MTSvgButton(mtApp, "data/arrowLeft.svg");
        rightButton = new MTSvgButton(mtApp, "data/arrowRight.svg");
        likeButton = new MTSvgButton(mtApp, "data/likeButton2.svg");
        dislikeButton = new MTSvgButton(mtApp, "data/dislikeButton2.svg");
        continueButton = new MTSvgButton(mtApp, "data/startButton.svg");

        ideaUser1.setSizeLocal(200f, 200f);
        ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150f, 0));

        leftButton.scale(0.5f, 0.5f, 0.5f, Vector3D.ZERO_VECTOR);
        leftButton.setPositionGlobal(new Vector3D(mtApp.width / 2 - 150, mtApp.height - 150));
        rightButton.scale(0.5f, 0.5f, 0.5f, Vector3D.ZERO_VECTOR);
        rightButton.setPositionGlobal(new Vector3D(mtApp.width / 2 + 150, mtApp.height - 150));
        likeButton.scale(1.5f, 1.5f, 1.5f, Vector3D.ZERO_VECTOR);
        likeButton.setPositionGlobal(new Vector3D(mtApp.width / 2 - 30, mtApp.height - 40));
        dislikeButton.scale(1.5f, 1.5f, 1.5f, Vector3D.ZERO_VECTOR);
        dislikeButton.setPositionGlobal(new Vector3D(mtApp.width / 2 + 30, mtApp.height - 40));

        continueButton.setPositionRelativeToParent(new Vector3D(mtApp.width / 2 + ideaUser1.getWidthXY(TransformSpace.LOCAL) + 150, mtApp.height - 150));

        getCanvas().addChild(ideaUser1);
        getCanvas().addChild(rightButton);
        getCanvas().addChild(leftButton);
        getCanvas().addChild(likeButton);
        getCanvas().addChild(dislikeButton);
        getCanvas().addChild(continueButton);
    }

    @Override
    public void startScene() {
        allIdeas = controller.getAllVisibleIdeasForCurrentProblem();

        ideaUser1.setText(allIdeas.get(ideaIndex).getDescription());
        ideaUser1.setSizeLocal(200f, 150f);
        ideaUser1.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - 150f, 0));

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

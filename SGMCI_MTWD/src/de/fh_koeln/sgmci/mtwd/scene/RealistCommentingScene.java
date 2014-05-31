package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistCommentingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import de.fh_koeln.sgmci.mtwd.customelements.RealistCommentingModeratorWorkplace;
import de.fh_koeln.sgmci.mtwd.model.Comment;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.3.0
 */
public class RealistCommentingScene extends AbstractMTWDScene {

    private RealistCommentingModeratorWorkplace moderatorWorkplace;

    public RealistCommentingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new RealistCommentingSceneController(this);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_wood.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {
        moderatorWorkplace = new RealistCommentingModeratorWorkplace(mtApp);
        moderatorWorkplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        moderatorWorkplace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - moderatorWorkplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20));
        getCanvas().addChild(moderatorWorkplace);

        final MTRectangle rectangle = new MTRectangle(mtApp, -100, 0, 30, 30);
        rectangle.translate(new Vector3D(0, 15, 0));
        rectangle.registerInputProcessor(new TapProcessor(mtApp));
        rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_STARTED) {
                            ((RealistCommentingSceneController) controller).addCommentToCurrentlySelectedIdea(moderatorWorkplace.getCommentTextArea().getText());
                            moderatorWorkplace.getCommentTextArea().setText("");
                            updateScene();
                        }
                        return false;
                    }
                });
        moderatorWorkplace.addChild(rectangle);

    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        moderatorWorkplace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        moderatorWorkplace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener());
        moderatorWorkplace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener());
        moderatorWorkplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        moderatorWorkplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, moderatorWorkplace.getCloseButton()));
        moderatorWorkplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        moderatorWorkplace.getContinueButton().addGestureListener(TapProcessor.class, new ContinueButtonListener());
        moderatorWorkplace.getLeftButton().addGestureListener(TapProcessor.class, new LeftButtonListener());
        moderatorWorkplace.getRightButton().addGestureListener(TapProcessor.class, new RightButtonListener());
    }

    @Override
    public void startScene() {
        updateScene();
    }

    @Override
    public void updateScene() {
        moderatorWorkplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user1Id));

        if (((RealistCommentingSceneController) controller).isFirstIdea()) {
            moderatorWorkplace.getLeftButton().setVisible(false);
        } else {
            moderatorWorkplace.getLeftButton().setVisible(true);
        }
        if (((RealistCommentingSceneController) controller).isLastIdea()) {
            moderatorWorkplace.getRightButton().setVisible(false);
        } else {
            moderatorWorkplace.getRightButton().setVisible(true);
        }

        moderatorWorkplace.setIdea(((RealistCommentingSceneController) controller).getCurrentlySelectedIdeaForCurrentProblem().getDescription());
        List<String> commentDescriptions = new LinkedList<String>();
        Collection<Comment> comments = ((RealistCommentingSceneController)controller).getAllCommentsForCurrentlySelectedIdea();
        for(Comment comment : comments) {
            commentDescriptions.add(comment.getDescription());
        }
        moderatorWorkplace.setComments(commentDescriptions);
    }

    public class AddWorkspaceButtonListener implements IGestureEventListener {

        private final String userId;

        public AddWorkspaceButtonListener(String userId) {
            this.userId = userId;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_STARTED) {
                controller.setUserActive(userId, true);
            }
            return false;
        }
    }

    public class HelpButtonListener implements IGestureEventListener {

        private final Popup helpPopup;

        public HelpButtonListener() {
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    moderatorWorkplace.addChild(helpPopup);
                    helpPopup.setText(((RealistCommentingSceneController) controller).getHelpText());
                    helpPopup.setPositionRelativeToParent(new Vector3D(moderatorWorkplace.getWidthXY(TransformSpace.LOCAL) / 2, -helpPopup.getHeight() / 2 - 10));
                    helpPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    helpPopup.setVisible(false);
                    moderatorWorkplace.removeChild(helpPopup);
                default:
                    break;
            }
            return false;
        }
    }

    public class ProblemButtonListener implements IGestureEventListener {

        private final Popup problemPopup;

        public ProblemButtonListener() {
            problemPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    moderatorWorkplace.addChild(problemPopup);
                    problemPopup.setText(controller.getCurrentProblemDescription());
                    problemPopup.setPositionRelativeToParent(new Vector3D(moderatorWorkplace.getWidthXY(TransformSpace.LOCAL) / 2, -problemPopup.getHeight() / 2 - 10));
                    problemPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    problemPopup.setVisible(false);
                    moderatorWorkplace.removeChild(problemPopup);
                default:
                    break;
            }
            return false;
        }
    }

    public class CloseWorkspaceButtonListener implements IGestureEventListener {

        private final String userId;

        public CloseWorkspaceButtonListener(String userId) {
            this.userId = userId;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapAndHoldEvent te = (TapAndHoldEvent) ge;
            if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                if (te.isHoldComplete()) {
                    controller.setUserActive(userId, false);
                }
            }
            return false;
        }
    }

    public class ContinueButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_STARTED) {
                ((RealistCommentingSceneController) controller).proceed();
            }
            return false;
        }
    }

    public class LeftButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                ((RealistCommentingSceneController) controller).setPreviousIdeaAsSelectedOne();
                updateScene();
            }
            return false;
        }
    }

    public class RightButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                ((RealistCommentingSceneController) controller).setNextIdeaAsSelectedOne();
                updateScene();
            }
            return false;
        }
    }
}

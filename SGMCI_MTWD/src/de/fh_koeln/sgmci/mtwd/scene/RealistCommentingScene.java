package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistCommentingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import de.fh_koeln.sgmci.mtwd.customelements.RealistCommentingModeratorWorkspace;
import de.fh_koeln.sgmci.mtwd.customelements.RealistCommentingUserWorkspace;
import de.fh_koeln.sgmci.mtwd.model.Comment;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
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

    private RealistCommentingModeratorWorkspace moderatorWorkspace;
    private RealistCommentingUserWorkspace user2Workspace;
    private RealistCommentingUserWorkspace user3Workspace;
    private RealistCommentingUserWorkspace user4Workspace;

    public RealistCommentingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new RealistCommentingSceneController(this);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_wood.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, false));
    }

    @Override
    public void createComponents() {
        moderatorWorkspace = new RealistCommentingModeratorWorkspace(mtApp);
        moderatorWorkspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        moderatorWorkspace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - moderatorWorkspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20));
        getCanvas().addChild(moderatorWorkspace);

        user2Workspace = new RealistCommentingUserWorkspace(mtApp);
        user2Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workspace.rotateZ(new Vector3D(user2Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user2Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 180);
        user2Workspace.setPositionGlobal(new Vector3D(mtApp.width / 2, user2Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, 0));
        getCanvas().addChild(user2Workspace);

        user3Workspace = new RealistCommentingUserWorkspace(mtApp);
        user3Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workspace.rotateZ(new Vector3D(user3Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user3Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 90);
        user3Workspace.setPositionGlobal(new Vector3D(user3Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height / 2, 0));
        getCanvas().addChild(user3Workspace);

        user4Workspace = new RealistCommentingUserWorkspace(mtApp);
        user4Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workspace.rotateZ(new Vector3D(user4Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user4Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), -90);
        user4Workspace.setPositionGlobal(new Vector3D(mtApp.width - user4Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height / 2, 0));
        getCanvas().addChild(user4Workspace);
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        moderatorWorkspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        moderatorWorkspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener());
        moderatorWorkspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener());
        moderatorWorkspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        moderatorWorkspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, moderatorWorkspace.getCloseButton()));
        moderatorWorkspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        moderatorWorkspace.getContinueButton().addGestureListener(TapProcessor.class, new ContinueButtonListener());
        moderatorWorkspace.getLeftButton().addGestureListener(TapProcessor.class, new LeftButtonListener());
        moderatorWorkspace.getRightButton().addGestureListener(TapProcessor.class, new RightButtonListener());
        moderatorWorkspace.getCommentTextArea().registerInputProcessor(new FlickProcessor());
        moderatorWorkspace.getCommentTextArea().addGestureListener(FlickProcessor.class, new SendButtonListener());

        user2Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workspace.getCloseButton()));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));

        user3Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workspace.getCloseButton()));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));

        user4Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workspace.getCloseButton()));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
    }

    @Override
    public void startScene() {
        updateScene();
    }

    @Override
    public void updateScene() {
        moderatorWorkspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user1Id));
        user2Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user2Id));
        user3Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user3Id));
        user4Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user4Id));

        if (((RealistCommentingSceneController) controller).isFirstIdea()) {
            moderatorWorkspace.getLeftButton().setVisible(false);
        } else {
            moderatorWorkspace.getLeftButton().setVisible(true);
        }
        if (((RealistCommentingSceneController) controller).isLastIdea()) {
            moderatorWorkspace.getRightButton().setVisible(false);
        } else {
            moderatorWorkspace.getRightButton().setVisible(true);
        }

        String idea = ((RealistCommentingSceneController) controller).getCurrentlySelectedIdeaForCurrentProblem().getDescription();
        String problem = controller.getCurrentProblemDescription();

        moderatorWorkspace.setIdea(idea);
        user2Workspace.setIdea(idea);
        user3Workspace.setIdea(idea);
        user4Workspace.setIdea(idea);

        user2Workspace.setProblem(problem);
        user3Workspace.setProblem(problem);
        user4Workspace.setProblem(problem);

        List<String> commentDescriptions = new LinkedList<String>();
        Collection<Comment> comments = ((RealistCommentingSceneController) controller).getAllCommentsForCurrentlySelectedIdea();
        for (Comment comment : comments) {
            commentDescriptions.add(comment.getDescription());
        }
        moderatorWorkspace.setComments(commentDescriptions);
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
                    moderatorWorkspace.addChild(helpPopup);
                    helpPopup.setText(((RealistCommentingSceneController) controller).getHelpText());
                    helpPopup.setPositionRelativeToParent(new Vector3D(moderatorWorkspace.getWidthXY(TransformSpace.LOCAL) / 2, -helpPopup.getHeight() / 2 - 10));
                    helpPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    helpPopup.setVisible(false);
                    moderatorWorkspace.removeChild(helpPopup);
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
                    moderatorWorkspace.addChild(problemPopup);
                    problemPopup.setText(controller.getCurrentProblemDescription());
                    problemPopup.setPositionRelativeToParent(new Vector3D(moderatorWorkspace.getWidthXY(TransformSpace.LOCAL) / 2, -problemPopup.getHeight() / 2 - 10));
                    problemPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    problemPopup.setVisible(false);
                    moderatorWorkspace.removeChild(problemPopup);
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

    public class SendButtonListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            FlickEvent e = (FlickEvent) ge;
            if (e.getId() == MTGestureEvent.GESTURE_ENDED && e.isFlick()) {
                switch (e.getDirection()) {
                    case NORTH_EAST:
                    case EAST:
                        ((RealistCommentingSceneController) controller).addCommentToCurrentlySelectedIdea(moderatorWorkspace.getCommentTextArea().getText());
                        moderatorWorkspace.getCommentTextArea().setText("");
                        updateScene();
                        break;
                }
            }
            return false;
        }
    }
}

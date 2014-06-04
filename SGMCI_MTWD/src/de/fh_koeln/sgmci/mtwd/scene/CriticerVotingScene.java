package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.CriticerVotingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.CriticerVotingUserWorkspace;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
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
public class CriticerVotingScene extends AbstractMTWDScene {

    private CriticerVotingUserWorkspace user1Workspace;
    private CriticerVotingUserWorkspace user2Workspace;
    private CriticerVotingUserWorkspace user3Workspace;
    private CriticerVotingUserWorkspace user4Workspace;

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
    public void createComponents() {
        user1Workspace = new CriticerVotingUserWorkspace(mtApp);
        user1Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workspace = new CriticerVotingUserWorkspace(mtApp);
        user2Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workspace = new CriticerVotingUserWorkspace(mtApp);
        user3Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workspace = new CriticerVotingUserWorkspace(mtApp);
        user4Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);

        user1Workspace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - user1Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, 0));
        getCanvas().addChild(user1Workspace);

        user2Workspace.rotateZ(new Vector3D(user2Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user2Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 180);
        user2Workspace.setPositionGlobal(new Vector3D(mtApp.width / 2, user2Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, 0));
        getCanvas().addChild(user2Workspace);

        user3Workspace.rotateZ(new Vector3D(user3Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user3Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 90);
        user3Workspace.setPositionGlobal(new Vector3D(user3Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height / 2, 0));
        getCanvas().addChild(user3Workspace);

        user4Workspace.rotateZ(new Vector3D(user4Workspace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user4Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), -90);
        user4Workspace.setPositionGlobal(new Vector3D(mtApp.width - user4Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height / 2, 0));
        getCanvas().addChild(user4Workspace);
    }
    
    @Override
    public void createEventListeners() {
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        user1Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user1Workspace));
        user1Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user1Workspace));
        user1Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workspace.getCloseButton()));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user1Id));

        user2Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user2Workspace));
        user2Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user2Workspace));
        user2Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workspace.getCloseButton()));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user2Id));

        user3Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user3Workspace));
        user3Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user3Workspace));
        user3Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workspace.getCloseButton()));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user3Id));

        user4Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user4Workspace));
        user4Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user4Workspace));
        user4Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workspace.getCloseButton()));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user4Id));
    }

    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user1Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user2Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user3Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user4Id, false);

        updateScene();

        user1Workspace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user1Id));
        user2Workspace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user2Id));
        user3Workspace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user3Id));
        user4Workspace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user4Id));
    }

    @Override
    public void updateScene() {
        user1Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user1Id));
        user2Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user2Id));
        user3Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user3Id));
        user4Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user4Id));

        user1Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user1Id));
        user2Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user2Id));
        user3Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user3Id));
        user4Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user4Id));
    }
    
    @Override
    public void shutDown() {
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

        private final CriticerVotingUserWorkspace workspace;
        private final Popup helpPopup;

        public HelpButtonListener(CriticerVotingUserWorkspace workspace) {
            this.workspace = workspace;
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workspace.addChild(helpPopup);
                    helpPopup.setText(((CriticerVotingSceneController) controller).getHelpText());
                    helpPopup.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, -helpPopup.getHeight() / 2 - 10));
                    helpPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    helpPopup.setVisible(false);
                    workspace.removeChild(helpPopup);
                default:
                    break;
            }
            return false;
        }
    }

    public class ProblemButtonListener implements IGestureEventListener {

        private final CriticerVotingUserWorkspace workspace;
        private final Popup problemPopup;

        public ProblemButtonListener(CriticerVotingUserWorkspace workspace) {
            this.workspace = workspace;
            problemPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workspace.addChild(problemPopup);
                    problemPopup.setText(controller.getCurrentProblemDescription());
                    problemPopup.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, -problemPopup.getHeight() / 2 - 10));
                    problemPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    problemPopup.setVisible(false);
                    workspace.removeChild(problemPopup);
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
                    ((CriticerVotingSceneController) controller).proceed();
                }
            }
            return false;
        }
    }

    public class ReadyButtonListener implements IGestureEventListener {

        private final String userId;

        public ReadyButtonListener(String userId) {
            this.userId = userId;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_STARTED) {
                controller.setUserReadyToContinue(userId, true);
                ((CriticerVotingSceneController) controller).proceed();
            }
            return false;
        }
    }

    public class ReadyButtonDoneListener implements IGestureEventListener {

        private final String userId;

        public ReadyButtonDoneListener(String userId) {
            this.userId = userId;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_STARTED) {
                controller.setUserReadyToContinue(userId, false);
            }
            return false;
        }
    }
}

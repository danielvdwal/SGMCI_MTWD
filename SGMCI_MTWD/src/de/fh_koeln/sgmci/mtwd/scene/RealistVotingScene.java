package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistVotingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import de.fh_koeln.sgmci.mtwd.customelements.workspace.RealistVotingUserWorkspace;
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
 * @author Nadim Khan, Ramon Victor
 * @version 0.3.0
 */
public class RealistVotingScene extends AbstractMTWDScene {

    private RealistVotingUserWorkspace user1Workspace;
    private RealistVotingUserWorkspace user2Workspace;
    private RealistVotingUserWorkspace user3Workspace;
    private RealistVotingUserWorkspace user4Workspace;

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
    public void createComponents() {
        user1Workspace = new RealistVotingUserWorkspace(mtApp);
        user1Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workspace = new RealistVotingUserWorkspace(mtApp);
        user2Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workspace = new RealistVotingUserWorkspace(mtApp);
        user3Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workspace = new RealistVotingUserWorkspace(mtApp);
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

        user1Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.USER1_ID));
        user1Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user1Workspace));
        user1Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user1Workspace));
        user1Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workspace.getCloseButton()));
        user1Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.USER1_ID));
        user1Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.USER1_ID));
        user1Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.USER1_ID));

        user2Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.USER2_ID));
        user2Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user2Workspace));
        user2Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user2Workspace));
        user2Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workspace.getCloseButton()));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.USER2_ID));
        user2Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.USER2_ID));
        user2Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.USER2_ID));

        user3Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.USER3_ID));
        user3Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user3Workspace));
        user3Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user3Workspace));
        user3Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workspace.getCloseButton()));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.USER3_ID));
        user3Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.USER3_ID));
        user3Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.USER3_ID));

        user4Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.USER4_ID));
        user4Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user4Workspace));
        user4Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user4Workspace));
        user4Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workspace.getCloseButton()));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.USER4_ID));
        user4Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.USER4_ID));
        user4Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.USER4_ID));
    }

    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.USER1_ID, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.USER2_ID, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.USER3_ID, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.USER4_ID, false);

        updateScene();

        user1Workspace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.USER1_ID));
        user2Workspace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.USER2_ID));
        user3Workspace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.USER3_ID));
        user4Workspace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.USER4_ID));
    }

    @Override
    public void updateScene() {
        user1Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.USER1_ID));
        user2Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.USER2_ID));
        user3Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.USER3_ID));
        user4Workspace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.USER4_ID));

        user1Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.USER1_ID));
        user2Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.USER2_ID));
        user3Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.USER3_ID));
        user4Workspace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.USER4_ID));
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

        private final RealistVotingUserWorkspace workspace;
        private final Popup helpPopup;

        public HelpButtonListener(RealistVotingUserWorkspace workspace) {
            this.workspace = workspace;
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workspace.addChild(helpPopup);
                    helpPopup.setText(((RealistVotingSceneController) controller).getHelpText());
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

        private final RealistVotingUserWorkspace workspace;
        private final Popup problemPopup;

        public ProblemButtonListener(RealistVotingUserWorkspace workspace) {
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
                    ((RealistVotingSceneController) controller).proceed();
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
                ((RealistVotingSceneController) controller).proceed();
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

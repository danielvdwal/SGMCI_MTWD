package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.CriticerVotingSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistVotingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.CriticerVotingUserWorkplace;
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

    private CriticerVotingUserWorkplace user1Workplace;
    private CriticerVotingUserWorkplace user2Workplace;
    private CriticerVotingUserWorkplace user3Workplace;
    private CriticerVotingUserWorkplace user4Workplace;

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
        user1Workplace = new CriticerVotingUserWorkplace(mtApp);
        user1Workplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workplace = new CriticerVotingUserWorkplace(mtApp);
        user2Workplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workplace = new CriticerVotingUserWorkplace(mtApp);
        user3Workplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workplace = new CriticerVotingUserWorkplace(mtApp);
        user4Workplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);

        user1Workplace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - user1Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, 0));
        getCanvas().addChild(user1Workplace);

        user2Workplace.rotateZ(new Vector3D(user2Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user2Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 180);
        user2Workplace.setPositionGlobal(new Vector3D(mtApp.width / 2, user2Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, 0));
        getCanvas().addChild(user2Workplace);

        user3Workplace.rotateZ(new Vector3D(user3Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user3Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 90);
        user3Workplace.setPositionGlobal(new Vector3D(user3Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height / 2, 0));
        getCanvas().addChild(user3Workplace);

        user4Workplace.rotateZ(new Vector3D(user4Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user4Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), -90);
        user4Workplace.setPositionGlobal(new Vector3D(mtApp.width - user4Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height / 2, 0));
        getCanvas().addChild(user4Workplace);
    }
    
    @Override
    public void createEventListeners() {
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        user1Workplace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user1Workplace));
        user1Workplace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user1Workplace));
        user1Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workplace.getCloseButton()));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user1Id));

        user2Workplace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user2Workplace));
        user2Workplace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user2Workplace));
        user2Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workplace.getCloseButton()));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user2Id));

        user3Workplace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user3Workplace));
        user3Workplace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user3Workplace));
        user3Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workplace.getCloseButton()));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user3Id));

        user4Workplace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user4Workplace));
        user4Workplace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user4Workplace));
        user4Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workplace.getCloseButton()));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user4Id));
    }

    
    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user1Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user2Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user3Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user4Id, false);

        updateScene();

        user1Workplace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user1Id));
        user2Workplace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user2Id));
        user3Workplace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user3Id));
        user4Workplace.fillVotedIdeas(((CriticerVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user4Id));
    }

    @Override
    public void updateScene() {
        user1Workplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user1Id));
        user2Workplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user2Id));
        user3Workplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user3Id));
        user4Workplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user4Id));

        user1Workplace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user1Id));
        user2Workplace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user2Id));
        user3Workplace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user3Id));
        user4Workplace.setIsReady(controller.isUserReadyToContinue(AbstractMTWDSceneController.user4Id));
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

        private final CriticerVotingUserWorkplace workplace;
        private final Popup helpPopup;

        public HelpButtonListener(CriticerVotingUserWorkplace workplace) {
            this.workplace = workplace;
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workplace.addChild(helpPopup);
                    helpPopup.setText(((CriticerVotingSceneController) controller).getHelpText());
                    helpPopup.setPositionRelativeToParent(new Vector3D(workplace.getWidthXY(TransformSpace.LOCAL) / 2, -helpPopup.getHeight() / 2 - 10));
                    helpPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    helpPopup.setVisible(false);
                    workplace.removeChild(helpPopup);
                default:
                    break;
            }
            return false;
        }
    }

    public class ProblemButtonListener implements IGestureEventListener {

        private final CriticerVotingUserWorkplace workplace;
        private final Popup problemPopup;

        public ProblemButtonListener(CriticerVotingUserWorkplace workplace) {
            this.workplace = workplace;
            problemPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workplace.addChild(problemPopup);
                    problemPopup.setText(controller.getCurrentProblemDescription());
                    problemPopup.setPositionRelativeToParent(new Vector3D(workplace.getWidthXY(TransformSpace.LOCAL) / 2, -problemPopup.getHeight() / 2 - 10));
                    problemPopup.setVisible(true);
                    break;
                case TapEvent.GESTURE_ENDED:
                    problemPopup.setVisible(false);
                    workplace.removeChild(problemPopup);
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

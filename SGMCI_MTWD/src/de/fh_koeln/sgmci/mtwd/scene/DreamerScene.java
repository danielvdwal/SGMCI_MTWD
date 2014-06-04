package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.DreamerSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.ClosablePopup;
import de.fh_koeln.sgmci.mtwd.customelements.Cloud;
import de.fh_koeln.sgmci.mtwd.customelements.DreamerUserWorkspace;
import de.fh_koeln.sgmci.mtwd.customelements.Popup;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeaTextException;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeasException;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
public class DreamerScene extends AbstractMTWDScene {

    private final Map<String, Cloud> displayedIdeas;
    private final Random random = new Random();
    private Popup errorMessagePopup;
    private DreamerUserWorkspace user1Workspace;
    private DreamerUserWorkspace user2Workspace;
    private DreamerUserWorkspace user3Workspace;
    private DreamerUserWorkspace user4Workspace;

    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new DreamerSceneController(this);
        displayedIdeas = new HashMap<String, Cloud>();
    }

    @Override
    public void createBackground() {
        // 2400 x 1600
        PImage backgroundImage = mtApp.loadImage("data/background_sky.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, false));
    }

    @Override
    public void createComponents() {
        user1Workspace = new DreamerUserWorkspace(mtApp);
        user1Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workspace = new DreamerUserWorkspace(mtApp);
        user2Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workspace = new DreamerUserWorkspace(mtApp);
        user3Workspace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workspace = new DreamerUserWorkspace(mtApp);
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

        errorMessagePopup = new ClosablePopup(mtApp);
        errorMessagePopup.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);

        getCanvas().addChild(errorMessagePopup);
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
        user1Workspace.getCloud().registerInputProcessor(new FlickProcessor());
        user1Workspace.getCloud().addGestureListener(FlickProcessor.class, new SendButtonListener(user1Workspace, FlickEvent.FlickDirection.NORTH));

        user2Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user2Workspace));
        user2Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user2Workspace));
        user2Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workspace.getCloseButton()));
        user2Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user2Id));
        user2Workspace.getCloud().registerInputProcessor(new FlickProcessor());
        user2Workspace.getCloud().addGestureListener(FlickProcessor.class, new SendButtonListener(user2Workspace, FlickEvent.FlickDirection.SOUTH));

        user3Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user3Workspace));
        user3Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user3Workspace));
        user3Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workspace.getCloseButton()));
        user3Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user3Id));
        user3Workspace.getCloud().registerInputProcessor(new FlickProcessor());
        user3Workspace.getCloud().addGestureListener(FlickProcessor.class, new SendButtonListener(user3Workspace, FlickEvent.FlickDirection.EAST));

        user4Workspace.getAddWorkspaceButton().addGestureListener(TapProcessor.class, new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getHelpButton().addGestureListener(TapProcessor.class, new HelpButtonListener(user4Workspace));
        user4Workspace.getProblemButton().addGestureListener(TapProcessor.class, new ProblemButtonListener(user4Workspace));
        user4Workspace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workspace.getCloseButton()));
        user4Workspace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getReadyButton().addGestureListener(TapProcessor.class, new ReadyButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getReadyButtonDone().addGestureListener(TapProcessor.class, new ReadyButtonDoneListener(AbstractMTWDSceneController.user4Id));
        user4Workspace.getCloud().registerInputProcessor(new FlickProcessor());
        user4Workspace.getCloud().addGestureListener(FlickProcessor.class, new SendButtonListener(user4Workspace, FlickEvent.FlickDirection.WEST));
    }

    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user1Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user2Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user3Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user4Id, false);

        updateScene();
    }

    @Override
    public void updateScene() {
        Collection<Idea> ideas = controller.getAllVisibleIdeasForCurrentProblem();
        for (Idea idea : ideas) {
            if (!displayedIdeas.containsKey(idea.getId())) {
                final Cloud newIdea = new Cloud(mtApp, false);
                newIdea.setTextAreaText(idea.getDescription());
                displayedIdeas.put(idea.getId(), newIdea);
                getCanvas().addChild(newIdea);
                newIdea.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
                float ideaWidth = newIdea.getWidthXYRelativeToParent();
                float ideaHeight = newIdea.getHeightXYRelativeToParent();
                float padding = user1Workspace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) + 50;
                float minXVal = padding + ideaWidth / 2;
                float minYVal = padding + ideaHeight / 2;
                float maxXVal = mtApp.getWidth() - 2 * padding - ideaWidth / 2;
                float maxYVal = mtApp.getHeight() - 2 * padding - ideaHeight / 2;
                float widthRange = maxXVal - minXVal;
                float heightRange = maxYVal - minYVal;
                float xPos = random.nextInt((int) widthRange) + minXVal;
                float yPos = random.nextInt((int) heightRange) + minYVal;
                newIdea.setPositionGlobal(new Vector3D(xPos, yPos));
            }
        }

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
        for (Cloud cloud : displayedIdeas.values()) {
            cloud.destroy();
        }
        displayedIdeas.clear();
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

        private final DreamerUserWorkspace workspace;
        private final Popup helpPopup;

        public HelpButtonListener(DreamerUserWorkspace workspace) {
            this.workspace = workspace;
            helpPopup = new Popup(mtApp);
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            switch (te.getId()) {
                case TapEvent.GESTURE_STARTED:
                    workspace.addChild(helpPopup);
                    helpPopup.setText(((DreamerSceneController) controller).getHelpText());
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

        private final DreamerUserWorkspace workspace;
        private final Popup problemPopup;

        public ProblemButtonListener(DreamerUserWorkspace workspace) {
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
                    try {
                        ((DreamerSceneController) controller).proceed();
                    } catch (NoIdeasException ex) {
                        errorMessagePopup.setText(ex.getMessage());
                        errorMessagePopup.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2));
                        errorMessagePopup.setVisible(true);
                        controller.setUserReadyToContinue(userId, false);
                    }
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
                try {
                    ((DreamerSceneController) controller).proceed();
                } catch (NoIdeasException ex) {
                    errorMessagePopup.setText(ex.getMessage());
                    errorMessagePopup.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2));
                    errorMessagePopup.setVisible(true);
                    controller.setUserReadyToContinue(userId, false);
                }
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

    public class SendButtonListener implements IGestureEventListener {

        private final DreamerUserWorkspace workspace;
        private final FlickEvent.FlickDirection flickDirection;

        public SendButtonListener(DreamerUserWorkspace workspace, FlickEvent.FlickDirection flickDirection) {
            this.workspace = workspace;
            this.flickDirection = flickDirection;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            FlickEvent e = (FlickEvent) ge;
            if (e.getId() == MTGestureEvent.GESTURE_ENDED && e.isFlick()) {
                if (e.getDirection() == flickDirection) {
                    try {
                        ((DreamerSceneController) controller).createIdea(workspace.getCloud().getTextArea().getText());
                        workspace.getCloud().getTextArea().setText("");
                    } catch (NoIdeaTextException ex) {
                        // do nothing
                    }
                }
            }
            return false;
        }
    }
}

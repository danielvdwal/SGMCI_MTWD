package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.DreamerSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.DreamerUserWorkplace;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeaTextException;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeasException;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public class DreamerScene extends AbstractMTWDScene {

    private final IFont ideaFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);
    private final Map<String, MTTextArea> displayedIdeas;
    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private MTTextArea errorMessageTextArea;
    private DreamerUserWorkplace user1Workplace;
    private DreamerUserWorkplace user2Workplace;
    private DreamerUserWorkplace user3Workplace;
    private DreamerUserWorkplace user4Workplace;

    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new DreamerSceneController(this);
        displayedIdeas = new HashMap<String, MTTextArea>();
    }

    @Override
    public void createBackground() {
        // 2400 x 1600
        PImage backgroundImage = mtApp.loadImage("data/background_sky.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {
        final IFont problemFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);

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

        user1Workplace = new DreamerUserWorkplace(mtApp);
        user1Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workplace = new DreamerUserWorkplace(mtApp);
        user2Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workplace = new DreamerUserWorkplace(mtApp);
        user3Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workplace = new DreamerUserWorkplace(mtApp);
        user4Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

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

        errorMessageTextArea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLACK, MTColor.WHITE));
        errorMessageTextArea.setFillColor(MTColor.WHITE);
        errorMessageTextArea.setStrokeColor(MTColor.BLACK);
        errorMessageTextArea.setVisible(false);

        getCanvas().addChild(errorMessageTextArea);
    }

    @Override
    public void createEventListeners() {
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        user1Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workplace.getCloseButton()));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user1Id));
        user1Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user1Workplace.getSendButton().addGestureListener(TapProcessor.class, new SendButtonListener(user1Workplace));

        user2Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workplace.getCloseButton()));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user2Workplace.getSendButton().addGestureListener(TapProcessor.class, new SendButtonListener(user2Workplace));

        user3Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workplace.getCloseButton()));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user3Workplace.getSendButton().addGestureListener(TapProcessor.class, new SendButtonListener(user3Workplace));

        user4Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workplace.getCloseButton()));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user4Workplace.getSendButton().addGestureListener(TapProcessor.class, new SendButtonListener(user4Workplace));
    }

    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user1Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user2Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user3Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user4Id, false);

        updateScene();

        // needs to be removed and set for each user
        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }

    @Override
    public void updateScene() {
        Collection<Idea> ideas = controller.getAllVisibleIdeasForCurrentProblem();
        for (Idea idea : ideas) {
            if (!displayedIdeas.containsKey(idea.getId())) {
                final MTTextArea newTextArea = new MTTextArea(mtApp, ideaFont);
                newTextArea.setText(idea.getDescription());
                displayedIdeas.put(idea.getId(), newTextArea);
                getCanvas().addChild(newTextArea);
            }
        }

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
        for (MTTextArea textArea : displayedIdeas.values()) {
            textArea.destroy();
        }
        displayedIdeas.clear();
    }

    public class AddWorkspaceButtonListener implements ActionListener {

        private final String userId;

        public AddWorkspaceButtonListener(String userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                controller.setUserActive(userId, true);
            }
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
                        errorMessageTextArea.setText(ex.getMessage());
                        errorMessageTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));
                        errorMessageTextArea.setVisible(true);
                        controller.setUserReadyToContinue(userId, false);
                    }
                }
            }
            return false;
        }
    }

    public class ReadyButtonListener implements ActionListener {

        private final String userId;

        public ReadyButtonListener(String userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                controller.setUserReadyToContinue(userId, true);
                try {
                    ((DreamerSceneController) controller).proceed();
                } catch (NoIdeasException ex) {
                    errorMessageTextArea.setText(ex.getMessage());
                    errorMessageTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));
                    errorMessageTextArea.setVisible(true);
                    controller.setUserReadyToContinue(userId, false);
                }
            }
        }
    }

    public class ReadyButtonDoneListener implements ActionListener {

        private final String userId;

        public ReadyButtonDoneListener(String userId) {
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                controller.setUserReadyToContinue(userId, false);
            }
        }
    }

    public class SendButtonListener implements IGestureEventListener {

        private final DreamerUserWorkplace workplace;

        public SendButtonListener(DreamerUserWorkplace workplace) {
            this.workplace = workplace;
        }

        @Override
        public boolean processGestureEvent(MTGestureEvent ge) {
            TapEvent te = (TapEvent) ge;
            if (te.getId() == TapEvent.GESTURE_DETECTED) {
                try {
                    ((DreamerSceneController) controller).createIdea(workplace.getTextArea().getText());
                    workplace.getTextArea().setText("");
                } catch (NoIdeaTextException ex) {
                    // do nothing
                }
            }
            return false;
        }
    }
}

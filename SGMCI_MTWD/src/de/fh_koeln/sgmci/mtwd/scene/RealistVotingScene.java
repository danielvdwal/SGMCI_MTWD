package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistVotingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.RealistVotingUserWorkplace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private RealistVotingUserWorkplace user1Workplace;
    private RealistVotingUserWorkplace user2Workplace;
    private RealistVotingUserWorkplace user3Workplace;
    private RealistVotingUserWorkplace user4Workplace;

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
        IFont problemFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);

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

        user1Workplace = new RealistVotingUserWorkplace(mtApp);
        user1Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workplace = new RealistVotingUserWorkplace(mtApp);
        user2Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workplace = new RealistVotingUserWorkplace(mtApp);
        user3Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workplace = new RealistVotingUserWorkplace(mtApp);
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

        user2Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workplace.getCloseButton()));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user2Id));
        user2Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user2Id));
        
        user3Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workplace.getCloseButton()));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user3Id));
        user3Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user3Id));
        
        user4Workplace.getAddWorkspaceButton().addActionListener(new AddWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workplace.getCloseButton()));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new CloseWorkspaceButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButton().addActionListener(new ReadyButtonListener(AbstractMTWDSceneController.user4Id));
        user4Workplace.getReadyButtonDone().addActionListener(new ReadyButtonDoneListener(AbstractMTWDSceneController.user4Id)); 
    }

    @Override
    public void startScene() {
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user1Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user2Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user3Id, false);
        controller.setUserReadyToContinue(AbstractMTWDSceneController.user4Id, false);

        updateScene();

        user1Workplace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user1Id));
        user2Workplace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user2Id));
        user3Workplace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user3Id));
        user4Workplace.fillVotedIdeas(((RealistVotingSceneController) controller).getAllVotedIdeasForUser(AbstractMTWDSceneController.user4Id));

        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
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
                    ((RealistVotingSceneController) controller).proceed();
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
                ((RealistVotingSceneController) controller).proceed();
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
}

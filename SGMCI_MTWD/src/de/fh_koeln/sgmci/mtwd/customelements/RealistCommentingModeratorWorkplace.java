package de.fh_koeln.sgmci.mtwd.customelements;

import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class RealistCommentingModeratorWorkplace extends MTRectangle {

    private static final String addWorkspaceButtonSvgFile = "data/plusButton.svg";
    private static final String helpButtonSvgFile = "data/helpButton.svg";
    private static final String problemButtonSvgFile = "data/problemButton.svg";
    private static final String closeButtonSvgFile = "data/closeButton.svg";
    private static final String startButtonSvgFile = "data/startButton.svg";
    private static final String leftButtonSvgFile = "data/arrowLeft.svg";
    private static final String rightButtonSvgFile = "data/arrowRight.svg";
    private static final float buttonScaleFactor = 1.4f;
    private static final float arrowButtonScaleFactor = 2.0f;

    private final MTSvgButton addWorkspaceButton;
    private final MTRectangle workspace;
    private final MTRectangle dashboardSpace;
    private final Cloud currentDisplayedIdea;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
    private final MTSvgButton closeButton;
    private final MTSvgButton startButton;
    private final MTSvgButton leftButton;
    private final MTSvgButton rightButton;
    private final List<Idea> ideas;
    private MTList commentList;
    private MTListCell commentListCell;
    private MTTextArea commentTextField;
    private AbstractKeyboard keyboard;
    private IFont problemFont;
    private IFont ideaFont;
    private IFont commentFont;
    private int currentIndex;

    public RealistCommentingModeratorWorkplace(PApplet pApplet) {
        super(pApplet, 400, 300);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        this.problemFont = FontManager.getInstance().createFont(pApplet, "arial.ttf", 30);
        this.ideaFont = FontManager.getInstance().createFont(pApplet, "arial.ttf", 40);
        this.commentFont = FontManager.getInstance().createFont(pApplet, "arial.ttf", 18);
        
        this.addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonSvgFile);
        this.workspace = new MTRectangle(pApplet, 400, 300);
        this.workspace.setNoFill(true);
        this.workspace.setNoStroke(true);
        this.workspace.setPickable(false);
        this.workspace.removeAllGestureEventListeners();
        this.workspace.unregisterAllInputProcessors();
        removeAllGestureEventListeners();
        unregisterAllInputProcessors();
        
        this.keyboard = new Keyboard(pApplet);
        
        this.commentTextField = new MTTextArea(pApplet, 0, 0, 200, 200, commentFont);
        this.commentTextField.setFillColor(MTColor.YELLOW);
        this.commentTextField.setEnableCaret(true);
        this.commentTextField.setPickable(false);
        
        this.commentList = new MTList(pApplet, 0, 0, keyboard.getWidthXY(TransformSpace.LOCAL)/2-10, 410);
        this.commentListCell = new MTListCell(pApplet, keyboard.getWidthXY(TransformSpace.LOCAL)/2-10, 410);
        
        this.dashboardSpace = new MTRectangle(pApplet, keyboard.getWidth(), 420);
        this.dashboardSpace.removeAllGestureEventListeners();
        this.dashboardSpace.unregisterAllInputProcessors();
        this.dashboardSpace.setNoFill(true);
        this.dashboardSpace.setNoStroke(true);
        this.dashboardSpace.setPickable(false);
        
        this.keyboard.setPickable(false);
        this.keyboard.addTextInputListener(commentTextField);
        
        this.currentDisplayedIdea = new Cloud(pApplet, false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        this.problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
        this.closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        this.startButton = new MTSvgButton(pApplet, startButtonSvgFile);
        this.leftButton = new MTSvgButton(pApplet, leftButtonSvgFile);
        this.rightButton = new MTSvgButton(pApplet, rightButtonSvgFile);

        this.ideas = new LinkedList<Idea>();
        this.currentIndex = 0;

        positionAllComponents();
        addEventListeners();
    }

    public void fillIdeas(Collection<Idea> ideas) {
        this.ideas.clear();
        this.ideas.addAll(ideas);
        this.currentIndex = 0;
        updateWorkspace();
    }

    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        workspace.setVisible(active);
    }

    public void setIsReady(boolean ready) {
        startButton.setVisible(!ready);
    }

    public MTSvgButton getAddWorkspaceButton() {
        return addWorkspaceButton;
    }

    public MTSvgButton getHelpButton() {
        return helpButton;
    }

    public MTSvgButton getProblemButton() {
        return problemButton;
    }

    public MTSvgButton getCloseButton() {
        return closeButton;
    }

    public MTSvgButton getStartButton() {
        return startButton;
    }

    private void positionAllComponents() {
        addChild(addWorkspaceButton);
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        addChild(workspace);
        workspace.addChild(keyboard);
        keyboard.addChild(helpButton);
        keyboard.addChild(problemButton);
        keyboard.addChild(closeButton);
        keyboard.addChild(startButton);
        
        commentList.addListElement(commentListCell);
        dashboardSpace.addChild(commentList);
        dashboardSpace.addChild(commentTextField);
        dashboardSpace.addChild(currentDisplayedIdea);
        dashboardSpace.addChild(leftButton);
        dashboardSpace.addChild(rightButton);
        
        workspace.addChild(dashboardSpace);
        dashboardSpace.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, -dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2));
        
        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        problemButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        startButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        leftButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        rightButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        keyboard.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), helpButton.getHeightXYRelativeToParent() - 30));
        problemButton.setPositionRelativeToParent(new Vector3D(-problemButton.getWidthXYRelativeToParent(), keyboard.getHeight() - problemButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + closeButton.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));
        startButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + startButton.getWidthXYRelativeToParent(), keyboard.getHeight() - startButton.getHeightXYRelativeToParent() + 30));
        commentList.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.75f + 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 -10));
        commentTextField.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.25f - 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) * 0.75f - 10));
        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.25f - 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) * 0.25f - 10));
        leftButton.setPositionRelativeToParent(new Vector3D(-leftButton.getWidthXYRelativeToParent(), dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 - 10));
        rightButton.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) + rightButton.getWidthXYRelativeToParent(), dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 - 10));
      
        
        workspace.setVisible(false);
    }

    private void addEventListeners() {
        leftButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                    if (currentIndex > 0) {
                        currentIndex--;
                        updateWorkspace();
                    }
                }
                return false;
            }
        });
        rightButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                    if (currentIndex < ideas.size() - 1) {
                        currentIndex++;
                        updateWorkspace();
                    }
                }
                return false;
            }
        });
    }

    private void updateWorkspace() {
        Idea idea = ideas.get(currentIndex);
        currentDisplayedIdea.setTextAreaText(idea.getDescription());

        if (currentIndex == 0 && ideas.size() == 1) {
            leftButton.setVisible(false);
            rightButton.setVisible(false);
        } else if (currentIndex == 0) {
            leftButton.setVisible(false);
            rightButton.setVisible(true);
        } else if (currentIndex == ideas.size() - 1) {
            rightButton.setVisible(false);
            leftButton.setVisible(true);
        } else {
            leftButton.setVisible(true);
            rightButton.setVisible(true);
        }
    }
}

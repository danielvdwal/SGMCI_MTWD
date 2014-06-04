package de.fh_koeln.sgmci.mtwd.customelements;

import java.util.Collection;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
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
public final class RealistCommentingModeratorWorkspace extends MTRectangle {

    private static final String addWorkspaceButtonSvgFile = "data/plusButton.svg";
    private static final String helpButtonSvgFile = "data/helpButton.svg";
    private static final String problemButtonSvgFile = "data/problemButton.svg";
    private static final String closeButtonSvgFile = "data/closeButton.svg";
    private static final String startButtonSvgFile = "data/startButton.svg";
    private static final String leftButtonSvgFile = "data/arrowLeft.svg";
    private static final String rightButtonSvgFile = "data/arrowRight.svg";
    private static final float buttonScaleFactor = 1.4f;
    private static final float arrowButtonScaleFactor = 2.0f;

    private final PApplet pApplet;
    private final MTSvgButton addWorkspaceButton;
    private final MTRectangle workspace;
    private final MTRectangle dashboardSpace;
    private final Cloud currentDisplayedIdea;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
    private final MTSvgButton closeButton;
    private final MTSvgButton continueButton;
    private final MTSvgButton leftButton;
    private final MTSvgButton rightButton;
    private final MTList commentList;
    private final MTTextArea commentTextArea;
    private final AbstractKeyboard keyboard;
    private final IFont commentFont;

    public RealistCommentingModeratorWorkspace(PApplet pApplet) {
        super(pApplet, 400, 300);
        this.pApplet = pApplet;
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

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

        this.commentTextArea = new MTTextArea(pApplet, 0, 0, keyboard.getWidthXY(TransformSpace.LOCAL) / 2 - 10, 200, commentFont);
        this.commentTextArea.setFillColor(MTColor.YELLOW);
        this.commentTextArea.setEnableCaret(true);
        this.commentTextArea.removeAllGestureEventListeners();
        this.commentTextArea.unregisterAllInputProcessors();

        this.commentList = new MTList(pApplet, 0, 0, keyboard.getWidthXY(TransformSpace.LOCAL) / 2 - 10, 410);

        this.dashboardSpace = new MTRectangle(pApplet, keyboard.getWidth(), 420);
        this.dashboardSpace.removeAllGestureEventListeners();
        this.dashboardSpace.unregisterAllInputProcessors();
        this.dashboardSpace.setNoFill(true);
        this.dashboardSpace.setNoStroke(true);
        this.dashboardSpace.setPickable(false);

        this.keyboard.setPickable(false);
        this.keyboard.addTextInputListener(commentTextArea);

        this.currentDisplayedIdea = new Cloud(pApplet, false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        this.problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
        this.closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        this.continueButton = new MTSvgButton(pApplet, startButtonSvgFile);
        this.leftButton = new MTSvgButton(pApplet, leftButtonSvgFile);
        this.rightButton = new MTSvgButton(pApplet, rightButtonSvgFile);

        positionAllComponents();
    }

    public void setIdea(String decription) {
        currentDisplayedIdea.setTextAreaText(decription);
    }

    public void setComments(Collection<String> comments) {
        commentList.removeAllListElements();
        for (String comment : comments) {
            final MTTextArea textArea = new MTTextArea(pApplet, commentFont);
            textArea.setNoFill(true);
            textArea.setNoStroke(true);
            textArea.setText(comment);
            final MTListCell listCell = new MTListCell(pApplet, textArea.getWidthXY(TransformSpace.LOCAL), textArea.getHeightXY(TransformSpace.LOCAL));
            listCell.setNoFill(true);
            listCell.setNoStroke(true);
            listCell.addChild(textArea);
            commentList.addListElement(listCell);
        }
    }

    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        workspace.setVisible(active);
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

    public MTSvgButton getContinueButton() {
        return continueButton;
    }

    public MTSvgButton getLeftButton() {
        return leftButton;
    }

    public MTSvgButton getRightButton() {
        return rightButton;
    }

    public MTTextArea getCommentTextArea() {
        return commentTextArea;
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
        keyboard.addChild(continueButton);

        dashboardSpace.addChild(commentList);
        dashboardSpace.addChild(commentTextArea);
        dashboardSpace.addChild(currentDisplayedIdea);
        dashboardSpace.addChild(leftButton);
        dashboardSpace.addChild(rightButton);

        workspace.addChild(dashboardSpace);
        dashboardSpace.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, -dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2));

        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        problemButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        continueButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        leftButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        rightButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        keyboard.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), helpButton.getHeightXYRelativeToParent() - 30));
        problemButton.setPositionRelativeToParent(new Vector3D(-problemButton.getWidthXYRelativeToParent(), keyboard.getHeight() - problemButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + closeButton.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));
        continueButton.setPositionRelativeToParent(new Vector3D(keyboard.getWidth() + continueButton.getWidthXYRelativeToParent(), keyboard.getHeight() - continueButton.getHeightXYRelativeToParent() + 30));
        commentList.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.75f + 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 - 10));
        commentTextArea.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.25f - 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) * 0.75f - 10));
        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) * 0.25f - 10, dashboardSpace.getHeightXY(TransformSpace.LOCAL) * 0.25f - 10));
        leftButton.setPositionRelativeToParent(new Vector3D(-leftButton.getWidthXYRelativeToParent(), dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 - 10));
        rightButton.setPositionRelativeToParent(new Vector3D(dashboardSpace.getWidthXY(TransformSpace.LOCAL) + rightButton.getWidthXYRelativeToParent(), dashboardSpace.getHeightXY(TransformSpace.LOCAL) / 2 - 10));

        workspace.setVisible(false);
    }
}

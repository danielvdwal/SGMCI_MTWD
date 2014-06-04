package de.fh_koeln.sgmci.mtwd.customelements;

import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class RealistCommentingUserWorkspace extends MTRectangle {

    private static final String addWorkspaceButtonSvgFile = "data/plusButton.svg";
    private static final String closeButtonSvgFile = "data/closeButton.svg";
    private static final float buttonScaleFactor = 1.4f;

    private final MTSvgButton addWorkspaceButton;
    private final MTRectangle workspace;
    private final Cloud currentDisplayedIdea;
    private final MTTextArea problemTextArea;
    private final MTSvgButton closeButton;

    public RealistCommentingUserWorkspace(PApplet pApplet) {
        super(pApplet, 200, 200);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        this.addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonSvgFile);
        this.workspace = new MTRectangle(pApplet, 200, 200);
        this.workspace.setNoFill(true);
        this.workspace.setNoStroke(true);
        this.workspace.setPickable(false);
        this.workspace.removeAllGestureEventListeners();
        this.workspace.unregisterAllInputProcessors();
        removeAllGestureEventListeners();
        unregisterAllInputProcessors();

        this.currentDisplayedIdea = new Cloud(pApplet, false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.problemTextArea = new MTTextArea(pApplet, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));
        this.closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);

        this.problemTextArea.setStrokeColor(MTColor.BLACK);
        this.problemTextArea.removeAllGestureEventListeners();
        this.problemTextArea.unregisterAllInputProcessors();

        positionAllComponents();
    }

    public void setProblem(String description) {
        problemTextArea.setText(description);
        problemTextArea.setPositionRelativeToParent(new Vector3D(-problemTextArea.getWidthXY(TransformSpace.LOCAL) - currentDisplayedIdea.getWidthXYRelativeToParent() / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
    }

    public void setIdea(String decription) {
        currentDisplayedIdea.setTextAreaText(decription);
    }

    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        workspace.setVisible(active);
    }

    public MTSvgButton getAddWorkspaceButton() {
        return addWorkspaceButton;
    }

    public MTSvgButton getCloseButton() {
        return closeButton;
    }

    private void positionAllComponents() {
        addChild(addWorkspaceButton);
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        addChild(workspace);
        workspace.addChild(currentDisplayedIdea);
        workspace.addChild(problemTextArea);
        workspace.addChild(closeButton);

        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);

        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        problemTextArea.setPositionRelativeToParent(new Vector3D(-problemTextArea.getWidthXY(TransformSpace.LOCAL) - currentDisplayedIdea.getWidthXYRelativeToParent() / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        closeButton.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2 + currentDisplayedIdea.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));

        workspace.setVisible(false);
    }
}

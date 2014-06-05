package de.fh_koeln.sgmci.mtwd.customelements.workspace;

import de.fh_koeln.sgmci.mtwd.customelements.Cloud;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class RealistCommentingUserWorkspace extends AbstractWorkspace {

    private final Cloud currentDisplayedIdea;
    private final MTTextArea problemTextArea;

    public RealistCommentingUserWorkspace(PApplet pApplet) {
        super(pApplet, 200, 200, false);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        this.currentDisplayedIdea = new Cloud(pApplet, false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.problemTextArea = new MTTextArea(pApplet, FontManager.getInstance().createFont(pApplet, "arial.ttf", 30));

        this.problemTextArea.setStrokeColor(MTColor.BLACK);
        this.problemTextArea.removeAllGestureEventListeners();
        this.problemTextArea.unregisterAllInputProcessors();

        positionAllComponents();
    }

    public void setProblem(String description) {
        problemTextArea.setText(description);
        problemTextArea.setPositionRelativeToParent(new Vector3D((-problemTextArea.getWidthXY(TransformSpace.LOCAL) - currentDisplayedIdea.getWidthXYRelativeToParent()) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
    }

    public void setIdea(String decription) {
        currentDisplayedIdea.setTextAreaText(decription);
    }

    private void positionAllComponents() {
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        workspace.addChild(currentDisplayedIdea);
        workspace.addChild(problemTextArea);
        workspace.addChild(closeButton);

        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);

        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        problemTextArea.setPositionRelativeToParent(new Vector3D((-problemTextArea.getWidthXY(TransformSpace.LOCAL) - currentDisplayedIdea.getWidthXYRelativeToParent()) / 2, workspace.getHeightXY(TransformSpace.LOCAL) / 2));
        closeButton.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2 + currentDisplayedIdea.getWidthXYRelativeToParent(), closeButton.getHeightXYRelativeToParent() - 30));
    }
}

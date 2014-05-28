package de.fh_koeln.sgmci.mtwd.customelements;

import de.fh_koeln.sgmci.mtwd.model.VotedIdea;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
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
    private static final String readyButtonSvgFile = "data/readyButton.svg";
    private static final String readyButtonDoneSvgFile = "data/readyButtonDone.svg";
    private static final String leftButtonSvgFile = "data/arrowLeft.svg";
    private static final String rightButtonSvgFile = "data/arrowRight.svg";
    private static final String likeButtonSvgFile = "data/likeButton.svg";
    private static final String likeButtonSelectedSvgFile = "data/likeButton_selected.svg";
    private static final String dislikeButtonSvgFile = "data/dislikeButton.svg";
    private static final String dislikeButtonSelectedSvgFile = "data/dislikeButton_selected.svg";
    private static final float buttonScaleFactor = 1.4f;
    private static final float arrowButtonScaleFactor = 2.0f;
    private static final float voteButtonScaleFactor = 2.0f;

    private final PApplet pApplet;
    private final MTSvgButton addWorkspaceButton;
    private final MTRectangle workspace;
    private final MTTextArea currentDisplayedIdea;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
    private final MTSvgButton closeButton;
    private final MTSvgButton readyButton;
    private final MTSvgButton readyButtonDone;
    private final MTSvgButton leftButton;
    private final MTSvgButton rightButton;
    private final MTSvgButton likeButton;
    private final MTSvgButton likeButtonSelected;
    private final MTSvgButton dislikeButton;
    private final MTSvgButton dislikeButtonSelected;
    private final List<VotedIdea> votedIdeas;
    private int currentIndex;

    public RealistCommentingModeratorWorkplace(PApplet pApplet) {
        super(pApplet, 400, 300);
        this.pApplet = pApplet;
        this.setNoFill(true);
        this.setNoStroke(true);
        this.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        IFont ideaFont = FontManager.getInstance().createFont(pApplet, "arial.ttf", 18);

        this.addWorkspaceButton = new MTSvgButton(pApplet, addWorkspaceButtonSvgFile);
        this.workspace = new MTRectangle(pApplet, 400, 300);
        this.workspace.setNoFill(true);
        this.workspace.setNoStroke(true);
        this.workspace.setPickable(false);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();
        this.currentDisplayedIdea = new MTTextArea(pApplet, ideaFont);
        this.currentDisplayedIdea.setNoStroke(false);
        this.currentDisplayedIdea.setPickable(false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        this.problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
        this.closeButton = new MTSvgButton(pApplet, closeButtonSvgFile);
        this.readyButton = new MTSvgButton(pApplet, readyButtonSvgFile);
        this.readyButtonDone = new MTSvgButton(pApplet, readyButtonDoneSvgFile);
        this.leftButton = new MTSvgButton(pApplet, leftButtonSvgFile);
        this.rightButton = new MTSvgButton(pApplet, rightButtonSvgFile);
        this.likeButton = new MTSvgButton(pApplet, likeButtonSvgFile);
        this.likeButtonSelected = new MTSvgButton(pApplet, likeButtonSelectedSvgFile);
        this.dislikeButton = new MTSvgButton(pApplet, dislikeButtonSvgFile);
        this.dislikeButtonSelected = new MTSvgButton(pApplet, dislikeButtonSelectedSvgFile);

        this.votedIdeas = new LinkedList<VotedIdea>();
        this.currentIndex = 0;

        positionAllComponents();
        addEventListeners();
    }

    public void fillVotedIdeas(Collection<VotedIdea> votedIdeas) {
        this.votedIdeas.clear();
        this.votedIdeas.addAll(votedIdeas);
        this.currentIndex = 0;
        updateWorkspace();
    }

    public void setIsActive(boolean active) {
        addWorkspaceButton.setVisible(!active);
        workspace.setVisible(active);
    }

    public void setIsReady(boolean ready) {
        readyButton.setVisible(!ready);
        readyButtonDone.setVisible(ready);
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

    public MTSvgButton getReadyButton() {
        return readyButton;
    }

    public MTSvgButton getReadyButtonDone() {
        return readyButtonDone;
    }

    private void positionAllComponents() {
        addChild(addWorkspaceButton);
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        addChild(workspace);
        workspace.addChild(currentDisplayedIdea);
        currentDisplayedIdea.setSizeLocal(200f, 200f);
        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) / 2));
        workspace.addChild(helpButton);
        workspace.addChild(problemButton);
        workspace.addChild(closeButton);
        workspace.addChild(readyButton);
        workspace.addChild(readyButtonDone);
        currentDisplayedIdea.addChild(leftButton);
        currentDisplayedIdea.addChild(rightButton);
        currentDisplayedIdea.addChild(likeButton);
        currentDisplayedIdea.addChild(likeButtonSelected);
        currentDisplayedIdea.addChild(dislikeButton);
        currentDisplayedIdea.addChild(dislikeButtonSelected);
        helpButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        problemButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        closeButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        readyButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        readyButtonDone.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        leftButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        rightButton.scale(arrowButtonScaleFactor, arrowButtonScaleFactor, arrowButtonScaleFactor, Vector3D.ZERO_VECTOR);
        likeButton.scale(voteButtonScaleFactor, voteButtonScaleFactor, voteButtonScaleFactor, Vector3D.ZERO_VECTOR);
        likeButtonSelected.scale(voteButtonScaleFactor, voteButtonScaleFactor, voteButtonScaleFactor, Vector3D.ZERO_VECTOR);
        dislikeButton.scale(voteButtonScaleFactor, voteButtonScaleFactor, voteButtonScaleFactor, Vector3D.ZERO_VECTOR);
        dislikeButtonSelected.scale(voteButtonScaleFactor, voteButtonScaleFactor, voteButtonScaleFactor, Vector3D.ZERO_VECTOR);
        helpButton.setPositionRelativeToParent(new Vector3D(-helpButton.getWidthXYRelativeToParent(), 50 + helpButton.getHeightXYRelativeToParent() - 30));
        problemButton.setPositionRelativeToParent(new Vector3D(-problemButton.getWidthXYRelativeToParent(), 300 - problemButton.getHeightXYRelativeToParent() + 30));
        closeButton.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) + closeButton.getWidthXYRelativeToParent(), 50 + closeButton.getHeightXYRelativeToParent() - 30));
        readyButton.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) + readyButton.getWidthXYRelativeToParent(), workspace.getHeightXY(TransformSpace.LOCAL) - readyButton.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) + readyButtonDone.getWidthXYRelativeToParent(), workspace.getHeightXY(TransformSpace.LOCAL) - readyButtonDone.getHeightXYRelativeToParent() + 30));
        readyButtonDone.setVisible(false);
        leftButton.setPositionRelativeToParent(new Vector3D(-leftButton.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) / 2));
        rightButton.setPositionRelativeToParent(new Vector3D(currentDisplayedIdea.getWidthXY(TransformSpace.LOCAL) + rightButton.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) / 2));
        likeButton.setPositionRelativeToParent(new Vector3D(currentDisplayedIdea.getWidthXY(TransformSpace.LOCAL) / 2 - likeButton.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) + likeButton.getHeightXYRelativeToParent()));
        likeButtonSelected.setPositionRelativeToParent(new Vector3D(currentDisplayedIdea.getWidthXY(TransformSpace.LOCAL) / 2 - likeButtonSelected.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) + likeButtonSelected.getHeightXYRelativeToParent()));
        likeButtonSelected.setVisible(false);
        dislikeButton.setPositionRelativeToParent(new Vector3D(currentDisplayedIdea.getWidthXY(TransformSpace.LOCAL) / 2 + dislikeButton.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) + dislikeButton.getHeightXYRelativeToParent()));
        dislikeButtonSelected.setPositionRelativeToParent(new Vector3D(currentDisplayedIdea.getWidthXY(TransformSpace.LOCAL) / 2 + dislikeButtonSelected.getWidthXYRelativeToParent(), currentDisplayedIdea.getHeightXY(TransformSpace.LOCAL) + dislikeButtonSelected.getHeightXYRelativeToParent()));
        dislikeButtonSelected.setVisible(false);
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
                    if (currentIndex < votedIdeas.size() - 1) {
                        currentIndex++;
                        updateWorkspace();
                    }
                }
                return false;
            }
        });
        likeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                    VotedIdea votedIdea = votedIdeas.get(currentIndex);
                    votedIdea.like();
                    updateWorkspace();
                }
                return false;
            }
        });
        dislikeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {

            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                if (mtge.getId() == TapEvent.GESTURE_ENDED) {
                    VotedIdea votedIdea = votedIdeas.get(currentIndex);
                    votedIdea.dislike();
                    updateWorkspace();
                }
                return false;
            }
        });
    }

    private void updateWorkspace() {
        VotedIdea votedIdea = votedIdeas.get(currentIndex);
        currentDisplayedIdea.setText(votedIdea.getDescription());
        currentDisplayedIdea.setSizeLocal(200, 200);

        if (votedIdea.isLiked()) {
            likeButton.setVisible(false);
            likeButtonSelected.setVisible(true);
            dislikeButton.setVisible(true);
            dislikeButtonSelected.setVisible(false);
        } else if (votedIdea.isDisliked()) {
            likeButton.setVisible(true);
            likeButtonSelected.setVisible(false);
            dislikeButton.setVisible(false);
            dislikeButtonSelected.setVisible(true);
        } else {
            likeButton.setVisible(true);
            likeButtonSelected.setVisible(false);
            dislikeButton.setVisible(true);
            dislikeButtonSelected.setVisible(false);
        }

        if (currentIndex == 0) {
            leftButton.setVisible(false);
            rightButton.setVisible(true);
        } else if (currentIndex == votedIdeas.size() - 1) {
            rightButton.setVisible(false);
            leftButton.setVisible(true);
        } else {
            leftButton.setVisible(true);
            rightButton.setVisible(true);
        }
    }
}

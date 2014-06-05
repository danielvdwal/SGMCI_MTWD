package de.fh_koeln.sgmci.mtwd.customelements.workspace;

import de.fh_koeln.sgmci.mtwd.customelements.Cloud;
import de.fh_koeln.sgmci.mtwd.model.VotedIdea;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public final class RealistVotingUserWorkspace extends AbstractWorkspace {

    private final MTRectangle ideaSpace;
    private final Cloud currentDisplayedIdea;
    private final MTSvgButton helpButton;
    private final MTSvgButton problemButton;
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

    public RealistVotingUserWorkspace(PApplet pApplet) {
        super(pApplet, 400, 300, false);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.removeAllGestureEventListeners();
        this.unregisterAllInputProcessors();

        this.ideaSpace = new MTRectangle(pApplet, 200, 200);
        this.ideaSpace.removeAllGestureEventListeners();
        this.ideaSpace.unregisterAllInputProcessors();
        this.ideaSpace.setNoFill(true);
        this.ideaSpace.setNoStroke(true);
        this.ideaSpace.setPickable(false);
        this.currentDisplayedIdea = new Cloud(pApplet, false);
        this.currentDisplayedIdea.removeAllGestureEventListeners();
        this.currentDisplayedIdea.unregisterAllInputProcessors();
        this.helpButton = new MTSvgButton(pApplet, helpButtonSvgFile);
        this.problemButton = new MTSvgButton(pApplet, problemButtonSvgFile);
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

    public void setIsReady(boolean ready) {
        readyButton.setVisible(!ready);
        readyButtonDone.setVisible(ready);
    }

    public MTSvgButton getHelpButton() {
        return helpButton;
    }

    public MTSvgButton getProblemButton() {
        return problemButton;
    }

    public MTSvgButton getReadyButton() {
        return readyButton;
    }

    public MTSvgButton getReadyButtonDone() {
        return readyButtonDone;
    }

    private void positionAllComponents() {
        addWorkspaceButton.scale(buttonScaleFactor, buttonScaleFactor, buttonScaleFactor, Vector3D.ZERO_VECTOR);
        addWorkspaceButton.setPositionRelativeToParent(new Vector3D(this.getWidthXY(TransformSpace.LOCAL) / 2, this.getHeightXY(TransformSpace.LOCAL) / 2));

        workspace.addChild(ideaSpace);
        ideaSpace.setPositionRelativeToParent(new Vector3D(workspace.getWidthXY(TransformSpace.LOCAL) / 2, ideaSpace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));
        ideaSpace.addChild(currentDisplayedIdea);
        currentDisplayedIdea.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) / 2, currentDisplayedIdea.getHeightXYRelativeToParent() / 2));
        workspace.addChild(helpButton);
        workspace.addChild(problemButton);
        workspace.addChild(closeButton);
        workspace.addChild(readyButton);
        workspace.addChild(readyButtonDone);
        ideaSpace.addChild(leftButton);
        ideaSpace.addChild(rightButton);
        ideaSpace.addChild(likeButton);
        ideaSpace.addChild(likeButtonSelected);
        ideaSpace.addChild(dislikeButton);
        ideaSpace.addChild(dislikeButtonSelected);
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
        leftButton.setPositionRelativeToParent(new Vector3D(-leftButton.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL)/ 2));
        rightButton.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) + rightButton.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL) / 2));
        likeButton.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) / 2 - likeButton.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL) + likeButton.getHeightXYRelativeToParent()));
        likeButtonSelected.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) / 2 - likeButtonSelected.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL) + likeButtonSelected.getHeightXYRelativeToParent()));
        likeButtonSelected.setVisible(false);
        dislikeButton.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) / 2 + dislikeButton.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL) + dislikeButton.getHeightXYRelativeToParent()));
        dislikeButtonSelected.setPositionRelativeToParent(new Vector3D(ideaSpace.getWidthXY(TransformSpace.LOCAL) / 2 + dislikeButtonSelected.getWidthXYRelativeToParent(), ideaSpace.getHeightXY(TransformSpace.LOCAL) + dislikeButtonSelected.getHeightXYRelativeToParent()));
        dislikeButtonSelected.setVisible(false);
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
        currentDisplayedIdea.setTextAreaText(votedIdea.getDescription());

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

        if (currentIndex == 0 && votedIdeas.size() == 1) {
            leftButton.setVisible(false);
            rightButton.setVisible(false);
        } else if (currentIndex == 0) {
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

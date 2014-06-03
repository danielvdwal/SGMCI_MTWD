package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.model.Comment;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import java.util.Collection;
import java.util.List;

/**
 * This class is used as the controller for the RealistCommentingScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the RealistCommentingScene.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class RealistCommentingSceneController extends AbstractMTWDSceneController {

    private static final String HELP_TEXT = "Auf diesem Screen k\u00F6nnen Sie Kommentare\n"
            + "zu der Machbarkeit der Ideen eintragen.\n"
            + "Wenn Sie ein Kommentar bes\u00E4tigen m\u00F6chten,\n"
            + "wischen Sie \u00FCber dem Kommentar mit dem Finger nach rechts.\n"
            + "Durch die Pfeile links und rechts\n"
            + "k\u00F6nnen Sie zwischen den Ideen wechseln.";
    private static int commentIdCounter = 0;
    private int index = 0;

    public RealistCommentingSceneController(IScene observer) {
        super(observer);
    }

    public String getHelpText() {
        return HELP_TEXT;
    }

    public boolean isFirstIdea() {
        return index == 0;
    }

    public boolean isLastIdea() {
        return index == getAllVisibleIdeasForCurrentProblem().size() - 1;
    }

    public void setNextIdeaAsSelectedOne() {
        List<Idea> ideas = getAllVisibleIdeasForCurrentProblem();
        if (index <= ideas.size()) {
            index++;
        }
    }

    public void setPreviousIdeaAsSelectedOne() {
        if (index > 0) {
            index--;
        }
    }

    public void setFirstIdeaAsSelectedOne() {
        index = 0;
    }

    public Idea getCurrentlySelectedIdeaForCurrentProblem() {
        return getAllVisibleIdeasForCurrentProblem().get(index);
    }

    public Collection<Comment> getAllCommentsForCurrentlySelectedIdea() {
        return getCurrentlySelectedIdeaForCurrentProblem().getAllComments();
    }

    public void addCommentToCurrentlySelectedIdea(String commentDescription) {
        getCurrentlySelectedIdeaForCurrentProblem().addComment(String.format("comment_%03d", commentIdCounter++), commentDescription);
    }

    @Override
    public void update() {
        setFirstIdeaAsSelectedOne();
    }

    public void proceed() {
        observer.gotoNextScene();
    }
}

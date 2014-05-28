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
 * @version 0.2.0
 */
public class RealistCommentingSceneController extends AbstractMTWDSceneController {

    private static int commentIdCounter = 0;
    private int index = 0;
            
    public RealistCommentingSceneController(IScene observer) {
        super(observer);
    }
    
    public void setNextIdeaAsSelectedOne() {
        List<Idea> ideas = getAllVisibleIdeasForCurrentProblem();
        if(index <= ideas.size()) {
            index++;
        }
    }
    
    public void setPreviousIdeaAsSelectedOne() {
        if(index > 0) {
            index--;
        }
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
}


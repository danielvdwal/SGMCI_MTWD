package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;

/**
 *
 * @author danielvanderwal
 */
public class Idea {
    
    private final String id;
    private final String description;
    private final CommentedIdea commentedIdea;
    private final CritiziedIdea critizedIdea;
    
    private boolean stillDisplayed;
    
    Idea(String id, String description) {
        this.id = id;
        this.description = id;
        this.commentedIdea = new CommentedIdea(this.id, this);
        this.critizedIdea = new CritiziedIdea(this.id, this);
    }
    
    // id
    public String getId() {    
        return id;
    }

    // description
    public String getDescription() {
        return description;
    }

    // comments
    public void addComment(String id, String description) {
        this.commentedIdea.addComment(id, description);
    }

    public void removeComment(String id) {
        this.commentedIdea.removeComment(id);
    }

    public Comment getComment(String id) {
        return commentedIdea.getComment(id);
    }

    public Collection<Comment> getAllComments() {
        return commentedIdea.getAllComments();
    }

    // critics
    public void addCritic(String id, String description) {
        this.critizedIdea.addCritic(id, description);
    }

    public void removeCritic(String id) {
        this.critizedIdea.removeCritic(id);
    }

    public Critic getCritic(String id) {
        return critizedIdea.getCritic(id);
    }

    public Collection<Critic> getAllCritics() {
        return critizedIdea.getAllCritics();
    }

    // stillDisplayed
    public boolean isStillDisplayed() {
        return stillDisplayed;
    }

    public void setStillDisplayed(boolean stillDisplayed) {
        this.stillDisplayed = stillDisplayed;
    }
}

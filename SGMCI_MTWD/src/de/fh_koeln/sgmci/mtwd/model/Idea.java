package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;

/**
 * This class represents an idea.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class Idea extends AbstractModel {
    
    private final String description;
    private final CommentedIdea commentedIdea;
    private final CritiziedIdea critizedIdea;
    
    private boolean stillDisplayed;
    
    /**
     * Creates a new idea object with the given id and description.
     * 
     * @param id the id of this object
     * @param description the description to be displayed
     */
    Idea(String id, String description) {
        super(id);
        this.description = description;
        this.commentedIdea = new CommentedIdea(this.id, this);
        this.critizedIdea = new CritiziedIdea(this.id, this);
    }

    // description
    /**
     * Get the description of this object.
     * 
     * @return the description of this object
     */
    public String getDescription() {
        return description;
    }

    // comments
    /**
     * Add a comment to this object.
     * 
     * @param id the id of the new comment to be created
     * @param description the description text of that comment
     */
    public void addComment(String id, String description) {
        this.commentedIdea.addComment(id, description);
    }

    /**
     * Remove the comment with the given id.
     * 
     * @param id the id of the comment to be removed
     */
    public void removeComment(String id) {
        this.commentedIdea.removeComment(id);
    }
    
    /**
     * Get the comment with the given id.
     * 
     * @param id the id of the comment to be retrieved
     * @return the comment with the given id
     */
    public Comment getComment(String id) {
        return commentedIdea.getComment(id);
    }

    /**
     * Retrieve all comments as a collection.
     * 
     * @return all comments of this object
     */
    public Collection<Comment> getAllComments() {
        return commentedIdea.getAllComments();
    }

    // critics
    /**
     * Add a critic to this object.
     * 
     * @param id the id of the new critic to be created
     * @param description the description text of that critic
     */
    public void addCritic(String id, String description) {
        this.critizedIdea.addCritic(id, description);
    }

    /**
     * Remove the critic with the given id.
     * 
     * @param id the id of the critic to be removed
     */
    public void removeCritic(String id) {
        this.critizedIdea.removeCritic(id);
    }
    
    /**
     * Get the critic with the given id.
     * 
     * @param id the id of the critic to be retrieved
     * @return the critic with the given id
     */
    public Critic getCritic(String id) {
        return critizedIdea.getCritic(id);
    }
    
    /**
     * Retrieve all critics as a collection.
     * 
     * @return all critics of this object
     */
    public Collection<Critic> getAllCritics() {
        return critizedIdea.getAllCritics();
    }

    // stillDisplayed
    /**
     * Is this idea still being displayed?
     * 
     * @return true if this idea should be displayed or false if not
     */
    public boolean isStillDisplayed() {
        return stillDisplayed;
    }

    /**
     * Change the value of the idea being displayed.
     * 
     * @param stillDisplayed the new value of the idea being displayed
     */
    public void setStillDisplayed(boolean stillDisplayed) {
        this.stillDisplayed = stillDisplayed;
    }
}

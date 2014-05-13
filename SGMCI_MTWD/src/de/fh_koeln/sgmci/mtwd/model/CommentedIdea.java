package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a container for the idea class 
 * to hold all comments of the associated idea object.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
class CommentedIdea extends AbstractModel {
    
    private final Idea idea;
    private final Map<String, Comment> comments;
    
    /**
     * Creates a new CommentedIdea object with the given id and idea.
     * 
     * @param id the id of this object
     * @param idea the idea that is associated with this object
     */
    CommentedIdea(String id, Idea idea) {
        super(id);
        this.idea = idea;
        this.comments = new HashMap<String, Comment>();
    }

    // description
    /**
     * Get the idea of this object.
     * 
     * @return the idea of this object
     */
    Idea getIdea() {
        return idea;
    }
    
    // comments
    /**
     * Add a comment to this object.
     * 
     * @param id the id of the new comment to be created
     * @param description the description text of that comment
     */
    void addComment(String id, String description) {
        this.comments.put(id, new Comment(id, description));
    }

    /**
     * Remove the comment with the given id.
     * 
     * @param id the id of the comment to be removed
     */
    void removeComment(String id) {
        this.comments.remove(id);
    }

    /**
     * Get the comment with the given id.
     * 
     * @param id the id of the comment to be retrieved
     * @return the comment with the given id
     */
    Comment getComment(String id) {
        return this.comments.get(id);
    }

    /**
     * Retrieve all comments as a collection.
     * 
     * @return all comments of this object
     */
    Collection<Comment> getAllComments() {
        return this.comments.values();
    }
}

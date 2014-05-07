package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author danielvanderwal
 */
class CommentedIdea {
    
    private final String id;
    private final Idea idea;
    private final Map<String, Comment> comments;
    
    CommentedIdea(String id, Idea idea) {
        this.id = id;
        this.idea = idea;
        this.comments = new HashMap<String, Comment>();
    }

    // id
    String getId() {
        return id;
    }

    // description
    Idea getIdea() {
        return idea;
    }
    
    // comments
    void addComment(String id, String description) {
        this.comments.put(id, new Comment(id, description));
    }

    void removeComment(String id) {
        this.comments.remove(id);
    }

    Comment getComment(String id) {
        return this.comments.get(id);
    }

    Collection<Comment> getAllComments() {
        return this.comments.values();
    }
    
}

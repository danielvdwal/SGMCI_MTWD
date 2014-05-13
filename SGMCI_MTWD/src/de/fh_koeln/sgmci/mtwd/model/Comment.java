package de.fh_koeln.sgmci.mtwd.model;

/**
 * This class represents a comment on an idea object.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class Comment extends AbstractModel {
    
    private final String description;

    /**
     * Creates a new Comment object with the given id and description.
     * 
     * @param id the id of this object
     * @param description the description to be displayed
     */
    public Comment(String id, String description) {
        super(id);
        this.description = description;
    }

    // description
    /**
     * Get the description text of this comment.
     * 
     * @return the description of this object
     */
    public String getDescription() {
        return description;
    }
}

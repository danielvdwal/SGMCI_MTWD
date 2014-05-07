package de.fh_koeln.sgmci.mtwd.model;

/**
 *
 * @author danielvanderwal
 */
public class Critic {
    
    private final String id;
    private final String description;

    public Critic(String id, String description) {
        this.id = id;
        this.description = description;
    }

    // id
    public String getId() {
        return id;
    }

    // description
    public String getDescription() {
        return description;
    }
}

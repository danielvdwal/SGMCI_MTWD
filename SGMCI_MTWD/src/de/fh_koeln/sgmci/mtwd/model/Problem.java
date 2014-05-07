package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author danielvanderwal
 */
public class Problem {

    private final String id;
    private final Map<String, Idea> ideas;
    
    private String description;

    public Problem(String id, String description) {
        this.id = id;
        this.ideas = new HashMap<String, Idea>();
        this.description = description;
    }

    // id
    public String getId() {
        return id;
    }

    // ideas
    public void addIdea(String id, String description) {
        this.ideas.put(id, new Idea(id, description));
    }

    public void removeIdea(String id) {
        this.ideas.remove(id);
    }

    public Idea getIdea(String id) {
        return ideas.get(id);
    }

    public Collection<Idea> getAllIdeas() {
        return ideas.values();
    }
    
    // description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

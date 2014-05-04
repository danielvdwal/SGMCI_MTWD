package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author danielvanderwal
 */
class CritiziedIdea {
    
    private final String id;
    private final Idea idea;
    private final Map<String, Critic> critics;
    
    CritiziedIdea(String id, Idea idea) {
        this.id = id;
        this.idea = idea;
        this.critics = new HashMap<String, Critic>();
    }

    // id
    String getId() {
        return id;
    }

    // description
    Idea getIdea() {
        return idea;
    }

    // critics
    void addCritic(String id, String description) {
        this.critics.put(id, new Critic(id, description));
    }
    
    void removeCritic(String id) {
        this.critics.remove(id);
    }

    Critic getCritic(String id) {
        return this.critics.get(id);
    }

    Collection<Critic> getAllCritics() {
        return this.critics.values();
    }
}

package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a container for the idea class 
 * to hold all critics of the associated idea object.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
class CritiziedIdea extends AbstractModel {

    private final Idea idea;
    private final Map<String, Critic> critics;

    /**
     * Creates a new CritiziedIdea object with the given id and idea.
     *
     * @param id the id of this object
     * @param idea the idea that is associated with this object
     */
    CritiziedIdea(String id, Idea idea) {
        super(id);
        this.idea = idea;
        this.critics = new HashMap<String, Critic>();
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

    // critics
    /**
     * Add a critic to this object.
     *
     * @param id the id of the new critic to be created
     * @param description the description text of that critic
     */
    void addCritic(String id, String description) {
        this.critics.put(id, new Critic(id, description));
    }

    /**
     * Remove the critic with the given id.
     *
     * @param id the id of the critic to be removed
     */
    void removeCritic(String id) {
        this.critics.remove(id);
    }

    /**
     * Get the critic with the given id.
     *
     * @param id the id of the critic to be retrieved
     * @return the critic with the given id
     */
    Critic getCritic(String id) {
        return this.critics.get(id);
    }

    /**
     * Retrieve all critics as a collection.
     *
     * @return all critics of this object
     */
    Collection<Critic> getAllCritics() {
        return this.critics.values();
    }
}

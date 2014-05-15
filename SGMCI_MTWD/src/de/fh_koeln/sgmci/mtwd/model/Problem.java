package de.fh_koeln.sgmci.mtwd.model;

import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class represents a problem.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class Problem extends AbstractModel {

    private final Map<String, Idea> ideas;
    private Idea newestIdea;

    private String description;

    /**
     * Creates a new problem with the given id and description.
     *
     * @param id the id for this object
     * @param description the description for this object
     */
    public Problem(String id, String description) {
        super(id);
        this.ideas = new HashMap<String, Idea>();
        this.description = description;
    }

    // ideas
    /**
     * Add an idea with the given id and description to this problem.
     *
     * @param id the id of the new idea
     * @param description the description for that idea
     */
    public void addIdea(String id, String description) {
        Idea idea = new Idea(id, description);
        this.ideas.put(id, idea);
        this.newestIdea = idea;
    }

    /**
     * Remove the idea the given id from the problem.
     *
     * @param id the id of the idea to be removed
     */
    public void removeIdea(String id) {
        this.ideas.remove(id);
    }

    /**
     * Get the idea with the given id.
     *
     * @param id the id of the idea to be retrieved
     * @return the idea with the given id
     */
    public Idea getIdea(String id) {
        return ideas.get(id);
    }

    /**
     * Get the newest idea that was added.<br >
     * And sets the newest idea to null.
     *
     * @return the newest idea
     */
    public Idea popNewestIdea() {
        Idea idea = newestIdea;
        newestIdea = null;
        return idea;
    }

    /**
     * Get all ideas as a collection that are used for this problem.
     *
     * @return all ideas used for this problem
     */
    public Collection<Idea> getAllIdeas() {
        return ideas.values();
    }

    /**
     * Get all ideas as a collection that are still visible and used for this
     * problem.
     *
     * @return all ideas that are still visible and used for this problem
     */
    public List<Idea> getAllVisibleIdeas() {
        List<Idea> visibleIdeas = new LinkedList<Idea>();
        for (Idea idea : ideas.values()) {
            visibleIdeas.add(idea);
        }
        return visibleIdeas;
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

    /**
     * Set the description of this object.
     *
     * @param description the description text for this problem
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The model class of the whole application.<br >
 * It is used to manage all problems and users in the application.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public final class MultitouchWaltDisneyApplication {

    private final Map<String, User> users;
    private final Map<String, Problem> problems;

    public MultitouchWaltDisneyApplication() {
        this.users = new HashMap<String, User>();
        this.problems = new HashMap<String, Problem>();
    }

    // users
    /**
     * Adds a user with the given id to the application.
     *
     * @param id the id of the new user
     */
    public void addUser(String id) {
        this.users.put(id, new User(id));
    }

    /**
     * Remove the user the given id from the application.
     *
     * @param id the id of the user to be removed
     */
    public void removeUser(String id) {
        this.users.remove(id);
    }

    /**
     * Get the user with the given id.
     *
     * @param id the id of the user to be retrieved
     * @return the user with the given id
     */
    public User getUser(String id) {
        return users.get(id);
    }

    /**
     * Get all users as a collection that are registered in the application.
     *
     * @return all users registered in the application
     */
    public Collection<User> getAllUsers() {
        return users.values();
    }

    // problem
    /**
     * Adds a problem with the given id and description to the application.
     *
     * @param id the id of the new problem
     * @param description the description of that problem
     */
    public void addProblem(String id, String description) {
        this.problems.put(id, new Problem(id, description));
    }

    /**
     * Remove the problem the given id from the application.
     *
     * @param id the id of the problem to be removed
     */
    public void removeProblem(String id) {
        this.problems.remove(id);
    }

    /**
     * Get the problem with the given id.
     *
     * @param id the id of the problem to be retrieved
     * @return the problem with the given id
     */
    public Problem getProblem(String id) {
        return problems.get(id);
    }

    /**
     * Get all problems as a collection that are registered in the application.
     *
     * @return all problems registered in the application
     */
    public Collection<Problem> getAllProblems() {
        return problems.values();
    }
}

package de.fh_koeln.sgmci.mtwd.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author danielvanderwal
 */
public final class MultitouchWaltDisneyApplication {

    private final Map<String, User> users;
    private final Map<String, Problem> problems;

    public MultitouchWaltDisneyApplication() {
        this.users = new HashMap<String, User>();
        this.problems = new HashMap<String, Problem>();
    }

    // users
    public void addUser(String id) {
        this.users.put(id, new User(id));
    }

    public void removeUser(String id) {
        this.users.remove(id);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    // problem
    public void addProblem(String id) {
        this.problems.put(id, new Problem(id));
    }

    public void removeProblem(String id) {
        this.problems.remove(id);
    }

    public Problem getProblem(String id) {
        return problems.get(id);
    }

    public Collection<Problem> getAllProblems() {
        return problems.values();
    }
}

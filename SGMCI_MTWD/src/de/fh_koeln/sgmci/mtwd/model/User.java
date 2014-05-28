package de.fh_koeln.sgmci.mtwd.model;

/**
 * This class represents a user.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class User extends AbstractModel {

    private boolean active;
    private boolean readyToContinue;
    
    public User(String id) {
        super(id);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isReadyToContinue() {
        return readyToContinue;
    }

    public void setReadyToContinue(boolean ready) {
        this.readyToContinue = ready;
    }
}

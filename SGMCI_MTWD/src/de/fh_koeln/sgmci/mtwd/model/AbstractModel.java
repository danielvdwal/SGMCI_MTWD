package de.fh_koeln.sgmci.mtwd.model;

/**
 * This class is used as the super class for all model classes.<br >
 * It provides an id field, which can be used by all models.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public abstract class AbstractModel {

    /**
     * The id of a model object.
     */
    final String id;

    /**
     * Creates a new AbstractModel object with the given id.
     *
     * @param id the id to be used for this object
     */
    public AbstractModel(String id) {
        this.id = id;
    }

    /**
     * Get the id of this object.
     *
     * @return the id of this object
     */
    public String getId() {
        return id;
    }
}

/**
 * 
 */

package com.example.jitvar.reapassignment.Core;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;


/**
 * Represents a real world entity that can be part of a well defined object model as well as has the capability
 * to interact with the the underlying persistence store.
 */
public abstract class Model {

    private static final String TAG = Model.class.getSimpleName();

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VERSION = "version";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_RESOURCE_ID = "resource_id";

    /**
     * a unique field that distinguishes between the Model instances. Caching depends upon this field
     * and the underlying orm framework also makes use of this field.
     */
    @DatabaseField(columnName = COLUMN_ID, generatedId = true, allowGeneratedIdInsert = true)
    private long id;

    /**
     * the version of the Model instance available. Useful for client - server applications
     * where the client needs to maintain the version of the data that it gets from the server end.
     */
    @DatabaseField(columnName = COLUMN_VERSION, canBeNull = true)
    private int version;

    /**
     * indicates whether this Model instance is active or no. Encourages soft delete among Model instances
     */
    @DatabaseField(columnName = COLUMN_ACTIVE, canBeNull = true, dataType = DataType.BOOLEAN, defaultValue = "1")
    private boolean active = true;

    /**
     * indicates the id of the resource represented by this <code>Model</code> instance.
     */
    @DatabaseField(columnName = COLUMN_RESOURCE_ID, canBeNull = true)
    private long resourceId;

    //setter and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * No-args constructor
     */
    public Model() {}

    /**
     * create an empty <code>Model</code> instance initialized with the id passed to it
     * @param id
     */
    public Model(long id) {
        this.id = id;
    }

    /**
     * Initialize a <code>Model</code> instance with the id, version passed to this constructor
     * @param id
     * @param version
     */
    public Model(long id, int version) {
        this.id = id;
        this.version = version;
    }

    /** Initialize a <code>Model</code> instance with the resourceId, version passed to this constructor
     * @param version
     * @param resourceId
     */
    public Model(int version, long resourceId) {
        this.version = version;
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return id == model.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [ID=" + id + "]";
    }


}

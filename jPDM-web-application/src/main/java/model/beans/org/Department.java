package model.beans.org;

import java.io.Serializable;
import java.util.UUID;

public class Department implements Serializable {
	private static final long serialVersionUID = 7581930084833685906L;
	public static final String DEFAULT = "default";
	private UUID id;
    private String name;
    private String description;

    public Department(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

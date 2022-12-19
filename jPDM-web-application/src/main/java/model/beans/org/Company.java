package model.beans.org;

import java.util.UUID;

public class Company {
    // Default name is forbidden to be removed from a database
    public static final String DEFAULT_COMPANY_NAME = "default";
    private UUID id;
    private String name;
    private String description;

    public Company() {

    }

    public Company(String name) {
        this.name = name;
        this.description = "";
        this.id = UUID.randomUUID();
    }

    public Company(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uid) {
        this.id = uid;
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

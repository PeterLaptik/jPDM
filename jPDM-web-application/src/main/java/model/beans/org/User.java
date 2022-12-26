package model.beans.org;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
	private static final long serialVersionUID = -1760783703833569643L;
	private UUID id;
    private String name;
    private String login;
    private UUID departmentId;

    public User(String login, String name) {
        this.login = login;
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }
}

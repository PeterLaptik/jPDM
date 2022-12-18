package model.beans.org;

import java.util.List;
import java.util.UUID;

public class Company {
	// Default name is forbidden to be removed from a database
	public static final String DEFAULT_COMPANY_NAME = "default";
	private UUID id;
	private String name;
	private String description;

	private List<Department> departments;
	private List<User> users;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}

package model.beans.org;

import java.util.UUID;

public class Department {
	private UUID id;
	private String name;

	public Department(String name) {
		this.name = name;
		id = UUID.randomUUID();
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
}

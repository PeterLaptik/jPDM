package model.beans.org;

import java.util.UUID;

public class Group {
	public static final String GROUP_DEFAULT = "default";
	public static final String GROUP_ADMIN = "ADMIN";
	public static final String GROUP_DBA_ADMIN = "DBA";

	private UUID id;
	private String name;
	private String description;

	public Group() {

	}

	public Group(String name, String description) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.description = description;
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

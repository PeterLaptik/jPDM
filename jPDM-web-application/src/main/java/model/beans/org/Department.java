package model.beans.org;

import java.io.Serializable;
import java.util.UUID;

public class Department implements Serializable {
	private static final long serialVersionUID = 7581930084833685906L;
	public static final int MAX_CHARS_NAME = 31;
	public static final int MAX_CHARS_DESCR = 63;
	public static final String DEFAULT = "default";
	private UUID id;
	private String name;
	private String description;

	public Department() {

	}

	public Department(String name) {
		this.name = name;
		this.description = "";
		this.id = UUID.randomUUID();
		shrinkValues();
	}

	public Department(String name, String description) {
		this.name = name;
		this.description = description;
		this.id = UUID.randomUUID();
		shrinkValues();
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

	private void shrinkValues() {
		if (name.length() > MAX_CHARS_NAME)
			name = name.substring(0, MAX_CHARS_NAME);
		if (description.length() > MAX_CHARS_DESCR)
			description = description.substring(0, MAX_CHARS_DESCR);
	}
}

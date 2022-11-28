CREATE TABLE entity_types(
	typename varchar(32),
	CONSTRAINT pkey_types PRIMARY KEY (typename)
);

CREATE TABLE entity_inheritance(
	base_typename varchar(32),
	derived_typename varchar(32),
	PRIMARY KEY (base_typename, derived_typename),
	CONSTRAINT fk_base_type FOREIGN KEY (base_typename) REFERENCES entity_types(typename),
	CONSTRAINT fk_derived_type FOREIGN KEY (derived_typename) REFERENCES entity_types(typename)
);

CREATE TABLE entity_properties(
	typename varchar(32),
	property_name varchar(32),
	is_master_property bool,
	property_type SMALLINT,
	CONSTRAINT fk_property_owner FOREIGN KEY (typename) REFERENCES entity_types(typename),
	PRIMARY KEY (typename, property_name, is_master_property)
);

CREATE TABLE parts(
	id bigint,
	part_type varchar(32),
	part_name varchar(128),
	part_description varchar(255),
	CONSTRAINT pkey_parts PRIMARY KEY (id),
	CONSTRAINT fk_part_types FOREIGN KEY (part_type) REFERENCES entity_types(typename)
);

CREATE TABLE parts_versions(
	master_id bigint,
	version_id bigint,
	version_name varchar(8),
	part_type varchar(32),
	version_description varchar(255),
	CONSTRAINT fk_master_part FOREIGN KEY (master_id) REFERENCES parts(id),
	CONSTRAINT fk_part_version_types FOREIGN KEY (part_type) REFERENCES entity_types(typename),
	PRIMARY KEY (master_id, version_id, version_name)
);

CREATE TABLE users(
	id bigint,
	user_login varchar(16),
	user_password varchar(32),
	user_salt varchar(8)
);
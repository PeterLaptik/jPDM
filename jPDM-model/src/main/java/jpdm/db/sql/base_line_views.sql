
-- Root types selection
CREATE VIEW pdm_root_types
AS
SELECT typename FROM entity_types
WHERE typename NOT IN
(SELECT derived_typename FROM entity_inheritance);


-- Leaf types selection
CREATE VIEW pdm_leaf_types
AS
SELECT typename FROM entity_types
WHERE typename NOT IN
(SELECT base_typename FROM entity_inheritance);


-- Middle types
CREATE VIEW pdm_middle_types
AS
SELECT typename FROM entity_types
WHERE 
	(typename NOT IN (SELECT typename FROM pdm_root_types))
	AND (typename NOT IN (SELECT typename FROM pdm_leaf_types));


-- Recursive list
CREATE VIEW pdm_type_hierarchy
AS
WITH RECURSIVE type_info(pseudo_parent_type, parent_type, child_type, 
				type_level, type_structure, type_list, property_list,
				master_properties, version_properties) AS
(SELECT
		typename, 
		('')::TEXT, 
		typename, 
		0, 
		typename::varchar,
		array[typename::varchar],
		CASE
			WHEN (SELECT count(*) FROM entity_properties WHERE entity_properties.typename = entity_types.typename)<1 THEN array[]::varchar[]
			ELSE (SELECT array_agg(property_name) FROM entity_properties WHERE entity_properties.typename = entity_types.typename)
		END,
		CASE
			WHEN (SELECT count(*) FROM entity_properties WHERE entity_properties.typename = entity_types.typename)<1 THEN array[]::varchar[]
			ELSE (SELECT array_agg(property_name) FROM entity_properties 
			WHERE entity_properties.typename = entity_types.typename AND entity_properties.is_master_property = TRUE)
		END,
		CASE
			WHEN (SELECT count(*) FROM entity_properties WHERE entity_properties.typename = entity_types.typename)<1 THEN array[]::varchar[]
			ELSE (SELECT array_agg(property_name) FROM entity_properties 
			WHERE entity_properties.typename = entity_types.typename AND entity_properties.is_master_property = FALSE)
		END
	FROM entity_types
	WHERE typename NOT IN (SELECT derived_typename FROM entity_inheritance)
UNION ALL
	SELECT
		entity_inheritance.base_typename, 
		entity_inheritance.base_typename, 
		entity_inheritance.derived_typename, 
		type_level+1, 
		lpad(' ', 3*(type_level+1))::varchar || entity_inheritance.derived_typename,
		type_list || entity_inheritance.derived_typename,
		property_list || (SELECT array_agg(property_name) FROM entity_properties WHERE entity_properties.typename = entity_inheritance.derived_typename),
		master_properties || (SELECT array_agg(property_name) FROM entity_properties 
		WHERE entity_properties.typename = entity_inheritance.derived_typename AND entity_properties.is_master_property = TRUE),
		version_properties || (SELECT array_agg(property_name) FROM entity_properties 
		WHERE entity_properties.typename = entity_inheritance.derived_typename AND entity_properties.is_master_property = FALSE)
	FROM type_info
	JOIN entity_inheritance ON entity_inheritance.base_typename = child_type)
SELECT
	type_structure AS type_hierarchy,
	child_type AS type_name, 
	parent_type AS super_type,
	type_list AS inheritance_chain,
	master_properties AS master_properties,
	version_properties AS version_properties
FROM type_info ORDER BY pseudo_parent_type, parent_type, child_type, type_level;
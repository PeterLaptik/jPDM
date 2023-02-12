package jpdm.db.modeller.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelTypeNode {
	public static final String ROOT_NAME = "ROOT";
	private String name;
	private int level = 0;
	private ModelTypeNode parent;
	private List<ModelTypeNode> children = new ArrayList<ModelTypeNode>();

	private List<ModelTypeProperty> properties = new ArrayList<ModelTypeProperty>();

	private ModelTypeNode(String typeName) throws ModelUpdatingException {
		Set<String> namesUsed = getUsedNamesList();
		if (namesUsed.contains(typeName))
			throw new ModelUpdatingException(String.format("Defined type name '%s' already exists.", typeName));

		parent = null;
		name = typeName.trim();
	}

	private ModelTypeNode(ModelTypeNode parent, String name) throws ModelUpdatingException {
		this.parent = parent;
		this.name = name.trim();
	}

	public static ModelTypeNode createRoot() throws ModelUpdatingException {
		return new ModelTypeNode(ROOT_NAME);
	}

	public ModelTypeNode addChild(String name) throws ModelUpdatingException {
		Set<String> namesUsed = getUsedNamesList();
		if (namesUsed.contains(name))
			throw new ModelUpdatingException(String.format("Defined type name '%s' already exists.", name));

		ModelTypeNode child = new ModelTypeNode(this, name);
		child.level = level + 1;
		children.add(child);
		return child;
	}

	public void removeChild(ModelTypeNode child) {
		children.remove(child);
	}

	public ModelTypeNode getParent() {
		return parent;
	}
	
	public ModelTypeNode findSubType(String sybType) {
		if(name.equals(sybType))
			return this;
		
		ModelTypeNode t = null;
		for(ModelTypeNode i: children) {
			t = i.findSubType(sybType);
			if(t!=null)
				return t;
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ModelUpdatingException {
		if (this.name.equals(name.trim()))
			return;

		Set<String> namesUsed = getUsedNamesList();
		if (namesUsed.contains(name))
			throw new ModelUpdatingException(String.format("Defined type name '%s' already exists.", name));

		this.name = name.trim();
	}

	public ModelTypeProperty addProperty(ModelTypeProperty property) throws ModelUpdatingException {
		checkPropertyName(property);
		properties.add(property);
		return property;
	}

	private void checkPropertyName(ModelTypeProperty property) throws ModelUpdatingException {
		ModelTypeNode currentNode = this;
		while (currentNode != null) {
			for (ModelTypeProperty prop : currentNode.properties) {
				if (prop.getName().equals(property.getName()))
					throw new ModelUpdatingException(
							String.format("The property name '%s' already exists.", property.getName()));
			}
			currentNode = currentNode.parent;
		}
	}

	public List<ModelTypeProperty> getProperties() {
		return properties;
	}

	public ModelTypeNode getRoot() {
		if (parent == null)
			return this;

		return parent.getRoot();
	}

	public void toConsole() {
		for (int i = 0; i < level; i++)
			System.out.print("-");
		System.out.print(name + ": " + properties.size() + " properties\n");
		for (ModelTypeNode child : children)
			child.toConsole();
	}

	public List<ModelTypeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ModelTypeNode> children) {
        this.children = children;
    }

    private Set<String> getUsedNamesList() {
		ModelTypeNode root = getRoot();
		Set<String> result = new HashSet<String>();
		return root.getUsedNamesList(result);
	}

	private Set<String> getUsedNamesList(Set<String> set) {
		set.add(name);
		for (ModelTypeNode i : children)
			i.getUsedNamesList(set);
		return set;
	}
}

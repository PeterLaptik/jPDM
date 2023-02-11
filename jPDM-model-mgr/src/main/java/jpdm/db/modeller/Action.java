package jpdm.db.modeller;

public class Action {
	private ActionType action;
	private TargetType target;
	private String value;
	private String additionalValue;
	private String modelName;

	public Action(ActionType action, TargetType target, String value, String additionalValue, String modelName) {
		this.action = action;
		this.target = target;
		this.value = value;
		this.additionalValue = additionalValue;
		this.modelName = modelName;
	}

	@Override
	public String toString() {
		return String.format("%s\t%s\t%s\t%s\t%s", action, target, value, additionalValue, modelName);
	}
}

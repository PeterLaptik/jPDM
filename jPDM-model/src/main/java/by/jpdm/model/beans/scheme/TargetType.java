package by.jpdm.model.beans.scheme;

public enum TargetType {
    TYPE(0), PROPERTY(1);

    private int target;

    TargetType(int target) {
        this.target = target;
    }

    int getTargetValue() {
        return target;
    }
}

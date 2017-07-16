package com.ssm.common.base.enums;

public enum Action {

    ADD, SAVE, EDIT, UPDATE, REMOVE, DELETE;

    public static Action fromType(String type) {
        for (Action action : Action.values()) {
            if (action.name().equalsIgnoreCase(type)) {
                return action;
            }
        }
        return null;
    }

}

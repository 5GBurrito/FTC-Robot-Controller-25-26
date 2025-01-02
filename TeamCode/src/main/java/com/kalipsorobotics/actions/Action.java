package com.kalipsorobotics.actions;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Action {

    protected ArrayList<Action> dependentActions = new ArrayList<>();
    protected boolean hasStarted = false;
    protected boolean isDone = false;

    protected String name;

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean getHasStarted() {
        return hasStarted;
    }

    public void setDependentActions(Action... actions) {
        if (dependentActions == null) {
            dependentActions = new ArrayList<>();
        }
        dependentActions.clear();
        Collections.addAll(dependentActions, actions);

    }

    public void setDependentActions(ArrayList<Action> actions) {
        if (dependentActions == null) {
            dependentActions = new ArrayList<>();
        }
        dependentActions.clear();
        for (Action a : actions) {
            dependentActions.add(a);
        }

    }

    ArrayList<Action> getDependentActions() {
        return this.dependentActions;
    }

    //updates the action
    public boolean updateCheckDone() {
        if (isDone) {
            return true;

        } //if done never update
        for (Action action : dependentActions) {
            if (!action.getIsDone()) {
                return false;
            } //if dependent action is not done never update
            //dont start if dependant action not finished
        }

        update();

        return updateIsDone();

    }

    public boolean dependentActionsDone() {
        for (Action a : dependentActions) {
            if (!a.isDone) {
                return false;
            }
        }
        return true;
    }

    private boolean updateIsDone() {
        isDone = checkDoneCondition();
        return isDone;
    }
    protected abstract boolean checkDoneCondition();

    //motor power, etc
    protected abstract void update();

    @Override
    public String toString() {
        return "Action{" +
                "name='" + name + '\'' + "status=" + isDone +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printWithDependentActions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("--->");
        for (Action a : dependentActions) {
            stringBuilder.append(a);
        }
        Log.d("action dependancies",  stringBuilder.toString());
    }
}

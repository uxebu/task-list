package com.codurance.training.tasks;

public class ActionFailed implements ActionResult {
    @Override
    public boolean failed() {
        return true;
    }
}

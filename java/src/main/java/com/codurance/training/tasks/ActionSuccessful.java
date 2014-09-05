package com.codurance.training.tasks;

public class ActionSuccessful implements ActionResult {
    @Override
    public boolean failed() {
        return false;
    }
}

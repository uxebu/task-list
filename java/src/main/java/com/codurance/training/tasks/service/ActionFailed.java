package com.codurance.training.tasks.service;

public class ActionFailed implements ActionResult {
    @Override
    public boolean failed() {
        return true;
    }

    @Override
    public long taskId() {
        throw new IllegalStateException("Action failed, so there is no taskId.");
    }
}

package com.codurance.training.tasks.service;

public class ActionSuccessful implements ActionResult {
    private long taskId;

    public ActionSuccessful(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean failed() {
        return false;
    }

    @Override
    public long taskId() {
        return taskId;
    }
}

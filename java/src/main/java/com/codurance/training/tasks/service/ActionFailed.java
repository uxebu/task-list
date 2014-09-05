package com.codurance.training.tasks.service;

public class ActionFailed implements ActionResult {
    @Override
    public boolean failed() {
        return true;
    }
}

package com.codurance.training.tasks.service;

public class ActionSuccessful implements ActionResult {
    @Override
    public boolean failed() {
        return false;
    }
}

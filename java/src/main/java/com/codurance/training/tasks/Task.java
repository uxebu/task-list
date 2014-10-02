package com.codurance.training.tasks;

public final class Task {
    private final long id;
    private final String description;

    public Task(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}

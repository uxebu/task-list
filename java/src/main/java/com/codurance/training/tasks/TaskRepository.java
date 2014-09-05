package com.codurance.training.tasks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository {
    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();

    private long lastId = 0;

    public Map<String, List<Task>> getTasks() {
        return tasks;
    }

    public long nextId() {
        return ++lastId;
    }

    public boolean projectWithNameExists(String project) {
        return tasks.containsKey(project);
    }
}

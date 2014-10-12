package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.util.*;

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

    public Collection<Task> findAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (List<Task> tasksOfProject : getTasks().values()) {
            allTasks.addAll(tasksOfProject);
        }
        return allTasks;
    }
}

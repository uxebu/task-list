package com.codurance.training.tasks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository {
    private static final Map<String, List<Task>> tasks = new LinkedHashMap<>();

    private static long lastId = 0;

    public static Map<String, List<Task>> getTasks() {
        return tasks;
    }

    public static long nextId() {
        return ++lastId;
    }
}

package com.codurance.training.tasks;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class TaskService {
    private PrintWriter out;

    public TaskService(PrintWriter out) {
        this.out = out;
    }

    void show() {
        for (Map.Entry<String, List<Task>> project : TaskRepository.getTasks().entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }
}

package com.codurance.training.tasks;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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

    void addProject(String name) {
        TaskRepository.getTasks().put(name, new ArrayList<Task>());
    }

    void addTask(String project, String description, TaskList taskList) {
        List<Task> projectTasks = TaskRepository.getTasks().get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(TaskRepository.nextId(), description, false));
    }
}

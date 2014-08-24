package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskService {
    private PrintWriter out;

    private TaskRepository taskRepository;

    public TaskService(PrintWriter out, TaskRepository taskRepository) {
        this.out = out;
        this.taskRepository = taskRepository;
    }

    void show() {
        for (Map.Entry<String, List<Task>> project : taskRepository.getTasks().entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    void addProject(String name) {
        taskRepository.getTasks().put(name, new ArrayList<Task>());
    }

    void addTask(String project, String description) {
        List<Task> projectTasks = taskRepository.getTasks().get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(taskRepository.nextId(), description, false));
    }

    void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : taskRepository.getTasks().entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    void check(String idString) {
        setDone(idString, true);
    }

    void uncheck(String idString) {
        setDone(idString, false);
    }
}

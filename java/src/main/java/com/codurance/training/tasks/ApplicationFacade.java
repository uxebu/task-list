package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ApplicationFacade {
    private PrintWriter out;

    private TaskRepository taskRepository;
    private TaskService taskService;

    public ApplicationFacade(PrintWriter out, TaskRepository taskRepository) {
        this.out = out;
        this.taskRepository = taskRepository;
        taskService = new TaskService(taskRepository);
    }

    public void show() {
        for (String project : taskService.findAllProjects()) {
            out.println(project);
            for (Task task : taskService.findAllTasksForProject(project)) {
                out.printf("    [%c] %d: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    public void addProject(String name) {
        taskRepository.getTasks().put(name, new ArrayList<Task>());
    }

    public void addTask(String project, String description) {
        Collection<Task> projectTasks = taskService.findAllTasksForProject(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        projectTasks.add(new Task(taskRepository.nextId(), description, false));
    }

    private void setDone(String idString, boolean done) {
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

    public void check(String idString) {
        setDone(idString, true);
    }

    public void uncheck(String idString) {
        setDone(idString, false);
    }
}

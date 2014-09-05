package com.codurance.training.tasks.facade;

import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.service.ActionResult;
import com.codurance.training.tasks.service.TaskService;

import java.io.PrintWriter;

public class ApplicationFacade {
    private PrintWriter out;
    private TaskService taskService;

    public ApplicationFacade(PrintWriter out, TaskService taskService) {
        this.out = out;
        this.taskService = taskService;
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
        taskService.addProject(name);
    }

    public void addTask(String project, String description) {
        ActionResult actionResult = taskService.addTaskToProject(project, description);
        if (actionResult.failed()) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
        }
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);
        ActionResult actionResult = taskService.setTaskDone(id, done);
        if (actionResult.failed()) {
            out.printf("Could not find a task with an ID of %d.", id);
            out.println();
        }
    }

    public void check(String idString) {
        setDone(idString, true);
    }

    public void uncheck(String idString) {
        setDone(idString, false);
    }
}

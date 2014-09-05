package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Collection<Task> findAllTasksForProject(String projectName) {
        Map<String, List<Task>> projectNameToTasksMap = taskRepository.getTasks();
        return projectNameToTasksMap.get(projectName);
    }

    public Collection<String> findAllProjects() {
        return taskRepository.getTasks().keySet();
    }

    public ActionResult addTaskToProject(String project, String description) {
        if (taskRepository.projectWithNameExists(project)) {
            Collection<Task> projectTasks = findAllTasksForProject(project);
            projectTasks.add(new Task(taskRepository.nextId(), description, false));
            return new ActionSuccessful();
        }
        return new ActionFailed();
    }

    public void addProject(String projectName) {
        taskRepository.getTasks().put(projectName, new ArrayList<Task>());
    }

    public ActionResult setTaskDone(int id, boolean done) {
        for (Map.Entry<String, List<Task>> project : taskRepository.getTasks().entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return new ActionSuccessful();
                }
            }
        }
        return new ActionFailed();
    }
}

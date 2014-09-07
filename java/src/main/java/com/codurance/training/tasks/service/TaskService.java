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

    public void addProject(String projectName) {
        taskRepository.getTasks().put(projectName, new ArrayList<Task>());
    }

    public Collection<String> findAllProjects() {
        return taskRepository.getTasks().keySet();
    }

    public Collection<Task> findAllTasksForProject(String projectName) {
        Map<String, List<Task>> projectNameToTasksMap = taskRepository.getTasks();
        return projectNameToTasksMap.get(projectName);
    }

    public ActionResult addTaskToProject(String project, String description) {
        if (taskRepository.projectWithNameExists(project)) {
            Collection<Task> projectTasks = findAllTasksForProject(project);
            projectTasks.add(new Task(taskRepository.nextId(), description, false));
            //TODO should maybe return the task that was created or at least its id
            return new ActionSuccessful();
        }
        return new ActionFailed();
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

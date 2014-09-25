package com.codurance.training.tasks.service;

import com.codurance.training.tasks.Task;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TaskServiceTest {

    public static final long NON_EXISTING_ID = 99L;
    private final TaskService taskService = new TaskService(new TaskRepository());

    @Test
    public void yieldsEmptyProjectListWhenNoProjectsWereAdded() throws Exception {
        Collection<String> allProjects = taskService.findAllProjects();
        assertThat(allProjects, is(empty()));
    }

    @Test
    public void returnsListOfProjectsThatWereAdded() throws Exception {
        taskService.addProject("first project");
        taskService.addProject("second project");

        Collection<String> allProjects = taskService.findAllProjects();
        assertThat(allProjects, contains("first project", "second project"));
    }

    @Test
    public void tryingToAddTaskForNonExistingProjectResultsInAnError() throws Exception {
        ActionResult actionResult = taskService.addTaskToProject("project", "a task");

        assertThat(actionResult.failed(), is(true));
    }

    @Test
    public void addingTaskToExistingProjectDoesNotResultInAnError() throws Exception {
        taskService.addProject("project");
        ActionResult actionResult = taskService.addTaskToProject("project", "a task");

        assertThat(actionResult.failed(), is(false));
    }

    @Test
    public void addedTaskCanBeFoundByReturnedTaskId() throws Exception {
        taskService.addProject("project");
        ActionResult actionResult = taskService.addTaskToProject("project", "a task");
        Task task = taskService.findTaskById(actionResult.taskId());

        assertThat(task, taskWithDescription("a task"));
    }

    @Test
    public void askingForTaskOfNonExistingProjectResultsInNull() throws Exception {
        Collection<Task> tasksForProject = taskService.findAllTasksForProject("project does not exist");

        assertThat(tasksForProject, is(nullValue()));
    }

    @Test
    public void retrievingTaskAddedToProject() throws Exception {
        taskService.addProject("project");
        taskService.addTaskToProject("project", "a task");

        Collection<Task> tasks = taskService.findAllTasksForProject("project");

        assertThat(tasks, contains(taskWithDescription("a task")));
    }

    @Test
    public void settingAnUndoneTaskToDone() throws Exception {
        taskService.addProject("project");
        ActionResult actionResult = taskService.addTaskToProject("project", "task to be done");

        long taskId = actionResult.taskId();
        Task task = taskService.findTaskById(taskId);
        assertThat(task.isDone(), is(false));

        ActionResult actionResultFromSettingTaskDone = taskService.setTaskDone(taskId, true);
        assertThat(actionResultFromSettingTaskDone.failed(), is(false));

        Task newFoundTask = taskService.findTaskById(taskId);
        assertThat(newFoundTask.isDone(), is(true));
    }

    @Test
    public void tryingToSettingANonExistingTaskToDoneResultInAnErrorWhenNoProjectTasksExist() throws Exception {
        ActionResult actionResult = taskService.setTaskDone(NON_EXISTING_ID, true);

        assertThat(actionResult.failed(), is(true));
    }

    @Test
    public void tryingToSettingANonExistingTaskToDoneResultInAnErrorWhenOtherProjectTasksExist() throws Exception {
        createProjectWithAnotherTask();

        ActionResult actionResult = taskService.setTaskDone(NON_EXISTING_ID, true);

        assertThat(actionResult.failed(), is(true));
    }

    private void createProjectWithAnotherTask() {
        taskService.addProject("other project");
        taskService.addTaskToProject("other project", "other task");
    }

    private Matcher<Task> taskWithDescription(String description) {
        return new FeatureMatcher<Task, String>(equalTo(description), "task with description", "description") {
            @Override
            protected String featureValueOf(Task task) {
                return task.getDescription();
            }
        };
    }
}

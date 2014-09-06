package com.codurance.training.tasks.service;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class TaskServiceTest {

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
}
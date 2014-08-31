package com.codurance.training.tasks;

import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

public class ApplicationFacadeTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintWriter printWriter = new PrintWriter(outputStream);
    private final ApplicationFacade applicationFacade = new ApplicationFacade(printWriter, new TaskRepository());

    @Test
    public void noProjectsResultsInEmptyOutput() throws Exception {
        applicationFacade.show();

        assertOutput(isEmptyString());
    }

    @Test
    public void oneProjectWithoutTasks() throws Exception {
        applicationFacade.addProject("my project");

        applicationFacade.show();

        assertOutput(is("my project\n" +
                "\n"));
    }

    @Test
    public void oneProjectWithOneTask() throws Exception {
        applicationFacade.addProject("my project");
        applicationFacade.addTask("my project", "task 1");

        applicationFacade.show();

        assertOutput(is("my project\n" +
                "    [ ] 1: task 1\n" +
                "\n"));
    }

    @Test
    public void oneProjectWithTwoTask() throws Exception {
        applicationFacade.addProject("my project");
        applicationFacade.addTask("my project", "task 1");
        applicationFacade.addTask("my project", "task 2");

        applicationFacade.show();

        assertOutput(is("my project\n" +
                "    [ ] 1: task 1\n" +
                "    [ ] 2: task 2\n" +
                "\n"));
    }

    @Test
    public void twoProjectsWithEachOneHavingOneTask() throws Exception {
        applicationFacade.addProject("my project 1");
        applicationFacade.addTask("my project 1", "task 1.1");

        applicationFacade.addProject("my project 2");
        applicationFacade.addTask("my project 2", "task 2.1");

        applicationFacade.show();

        assertOutput(is("my project 1\n" +
                "    [ ] 1: task 1.1\n" +
                "\n" +
                "my project 2\n" +
                "    [ ] 2: task 2.1\n\n"));
    }

    private void assertOutput(Matcher<String> matcher) {
        printWriter.flush();
        assertThat(outputStream.toString(), matcher);
    }
}

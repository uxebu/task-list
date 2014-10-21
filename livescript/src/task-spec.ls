TaskService = require './task-service'

describe 'test setup' (void) ->
  it 'works' ->
    expect true .toBe true

# porting the java app, by `converting` the tests to a LiveScript style
describe 'tasks' (void) !->
  taskService = null
  beforeEach !->
    taskService := new TaskService()

  # public void yieldsEmptyProjectListWhenNoProjectsWereAdded() throws Exception {
  it 'yields empty project list when no projects were added' !->
    expect taskService.findAllProjects() .toEqual []

  # public void returnsListOfProjectsThatWereAdded() throws Exception {
  it 'returns list of projects that were added' !->
    taskService.addProject('first project');
    taskService.addProject('second project');

    projects = taskService.findAllProjects()
    expect projects .toContain 'first project'
    expect projects .toContain 'second project'

  # public void tryingToAddTaskForNonExistingProjectResultsInAnError() throws Exception {
  it 'trying to add task for non existing project results in an error' !->
    expect !->
      taskService.addTaskToProject 'project' 'a task'
    .toThrow()

  # public void addingTaskToExistingProjectDoesNotResultInAnError() throws Exception {
  it 'adding task to existing project does not result in an error' !->
    taskService.addProject 'project'
    expect !->
      taskService.addTaskToProject 'project' 'a task'
    .not.toThrow()

  # public void addedTaskCanBeFoundByReturnedTaskId() throws Exception {
  it 'added task can be found by returned task id' !->
    taskService.addProject 'project'
    taskId = taskService.addTaskToProject 'project' 'a task'
    task = taskService.findTaskById taskId
    expect task.name .toBe 'a task'

  # public void findByIdReturnsNullWhenNoProjectsExist() throws Exception {
  it 'find by id returns null when no projects exist' !->
    task = taskService.findTaskById 32
    expect task .toBe null

  # public void findByIdReturnsNullWhenNoTaskWithGivenIdExists() throws Exception {
  it 'find by id returns null when no task with given id exists' !->
    taskService.addProject 'project'
    taskId = taskService.addTaskToProject 'project' 'a task'
    task = taskService.findTaskById taskId+1
    expect task .toBe null

  # public void askingForTaskOfNonExistingProjectResultsInNull() throws Exception {
  it 'asking for task of non esisting project results in null' !->
    tasksForProject = taskService.findAllTasksForProject 'project does not exist'

    expect tasksForProject .toBe null

  # public void retrievingTaskAddedToProject() throws Exception {
  it 'retrieve task added to project' !->
    taskService.addProject 'project'
    taskService.addTaskToProject 'project' 'a task'

    tasks = taskService.findAllTasksForProject 'project'

    expect tasks[0].name .toBe 'a task'

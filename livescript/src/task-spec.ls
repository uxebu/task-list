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

class TaskService

  ->
    @_projects = {}

  _projects : null
  _taskIdCounter: 0

  addProject: (projectName) ->
    @_projects[projectName] = []

  addTaskToProject: (projectName, taskName) ->
    unless @_projects[projectName]
      throw Error
    task = @_createTask taskName
    @_projects[projectName].push task
    task.id

  _createTask: (taskName) ->
    {name: taskName, id: @_taskIdCounter++}

  findTaskById: (taskId) ->
    tasks = []
    for projectName of @_projects
      tasks ++= [task for task in @_projects[projectName] when task.id == taskId]
    tasks[0]

  findAllProjects: ->
    Object.keys @_projects

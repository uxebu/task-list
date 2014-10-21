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

class TaskService

  ->
    @_projects = {}

  _projects : null

  addProject: (projectName) ->
    @_projects[projectName] = []

  addTaskToProject: (projectName, taskName) ->
    unless @_projects[projectName]
      throw Error
    @_projects[projectName].push taskName

  findAllProjects: ->
    Object.keys @_projects

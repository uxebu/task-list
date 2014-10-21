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


class TaskService

  ->
    @_tasks = []

  _tasks : null

  addProject: (projectName) ->
    @_tasks.push projectName

  addTaskToProject: ->
    throw Error

  findAllProjects: ->
    @_tasks

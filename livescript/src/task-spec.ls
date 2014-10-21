describe 'test setup' (void) ->
  it 'works' ->
    expect true .toBe true

# porting the java app, by `converting` the tests to a LiveScript style
describe 'tasks' (void) ->
  taskService = null
  beforeEach !->
    taskService := new TaskService()

  # public void yieldsEmptyProjectListWhenNoProjectsWereAdded() throws Exception {
  it 'no projects cant return any tasks' ->
    expect taskService.findAllProjects() .toEqual []

  # public void returnsListOfProjectsThatWereAdded() throws Exception {
  it 'no projects cant return any tasks' ->
    taskService.addProject('first project');
    taskService.addProject('second project');

    projects = taskService.findAllProjects()
    expect projects .toContain 'first project'
    expect projects .toContain 'second project'



class TaskService

  ->
    @_tasks = []

  _tasks : null

  addProject: (projectName) ->
    @_tasks.push projectName

  findAllProjects: ->
    @_tasks

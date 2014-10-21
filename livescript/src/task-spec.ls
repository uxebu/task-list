describe 'test setup' (void) ->
  it 'works' ->
    expect true .toBe true

describe 'tasks' (void) ->
  tasks = null
  beforeEach !->
    tasks := new Tasks()
  it 'no projects cant return any tasks' ->
    expect(tasks.getAll()).toEqual([])



class Tasks
  getAll: ->
    []

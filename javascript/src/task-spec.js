var TaskService = require('./task-service');
var DeadlineNotToday = require('./deadline').DeadlineNotToday;
var DeadlineToday = require('./deadline').DeadlineToday;

describe('test setup', function(){
  it('works', function(){
    expect(true).toBe(true);
  });
});

describe('tasks', function(){

  var taskService;
  beforeEach(function(){
    taskService = new TaskService();
  });

  it('yields empty project list when no projects were added', function(){
    expect(taskService.findAllProjects()).toEqual([]);
  });

  it('returns list of projects that were added', function(){
    taskService.addProject('first project');
    taskService.addProject('second project');

    var projects = taskService.findAllProjects();

    expect(projects).toContain('first project');
    expect(projects).toContain('second project');
  });

  it('trying to add task for non existing project results in an error', function(){
    expect(function(){
      taskService.addTaskToProject('project', 'a task');
    }).toThrow();
  });

  it('adding task to existing project does not result in an error', function(){
    taskService.addProject('project');
    expect(function(){
      taskService.addTaskToProject('project', 'a task');
    }).not.toThrow();
  });

  it('added task can be found by returned task id', function(){
    taskService.addProject('project');
    var taskId = taskService.addTaskToProject('project', 'a task');

    var task = taskService.findTaskById(taskId);

    expect(task.name).toBe('a task');
  });

  it('find by id returns null when no projects exist', function(){
    var task = taskService.findTaskById(32);

    expect(task).toBe(null);
  });

  it('find by id returns null when no task with given id exists', function(){
    taskService.addProject('project');
    var taskId = taskService.addTaskToProject('project', 'a task');

    var task = taskService.findTaskById(taskId + 1);

    expect(task).toBe(null);
  });

  it('asking for task of non esisting project results in null', function(){
    var tasksForProject = taskService.findAllTasksForProject('project does not exist');

    expect(tasksForProject).toBe(null);
  });

  it('retrieve task added to project', function(){
    taskService.addProject('project');
    taskService.addTaskToProject('project', 'a task');

    var tasks = taskService.findAllTasksForProject('project');

    expect(tasks[0].name).toBe('a task');
  });

  describe('deadline', function() {
    var emptyTasksSet = [];

    it('no deadlines should report none', function() {
      expect(taskService.showTasksDueToday()).toEqual(emptyTasksSet);
    });

    it('task without deadline should report none', function() {
      taskService.addProject('project');
      taskService.addTaskToProject('project', 'a task');
      expect(taskService.showTasksDueToday()).toEqual(emptyTasksSet);
    });

    it('today deadline should report that task', function() {
      taskService.addProject('project');
      var taskId = taskService.addTaskToProject('project', 'a task');
      var task = taskService.findTaskById(taskId);
      taskService.addDeadlineToTask(taskId, new DeadlineToday());
      expect(taskService.showTasksDueToday()).toEqual([task]);
    });

    it('NOT today deadline should report that task', function() {
      taskService.addProject('project');
      var taskId = taskService.addTaskToProject('project', 'a task');
      var task = taskService.findTaskById(taskId);
      taskService.addDeadlineToTask(taskId, new DeadlineNotToday());
      expect(taskService.showTasksDueToday()).toEqual([]);
    });

    it('two with deadline today', function() {
      taskService.addProject('project');
      var firstTaskId = taskService.addTaskToProject('project', 'a task with deadline');
      taskService.addDeadlineToTask(firstTaskId, new DeadlineToday());
      var firstTask = taskService.findTaskById(firstTaskId);

      var taskId = taskService.addTaskToProject('project', 'a task with deadline');
      var task = taskService.findTaskById(taskId);
      taskService.addDeadlineToTask(taskId, new DeadlineToday());

      expect(taskService.showTasksDueToday()).toEqual([firstTask, task]);
    });



  });
  
});

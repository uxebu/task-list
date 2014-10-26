var Task = require('./task');
var DeadlineToday = require('./deadline').DeadlineToday;

function TaskService() {
  this._projects = {};
  this._tasksDueToday = [];
}
TaskService.prototype = {

  _projects: null,
  _taskIdCounter: 0,
  _tasksDueToday: null,

  addProject: function(projectName) {
    return this._projects[projectName] = [];
  },

  addTaskToProject: function(projectName, taskName) {
    if (!this._projects[projectName]) {
      throw Error;
    }
    var task = new Task(taskName);
    this._projects[projectName].push(task);
    return task.id;
  },

  addDeadlineToTask: function(taskId, deadline) {
    if (deadline instanceof DeadlineToday) {
      this._tasksDueToday.push(taskId);
    }
  },

  showTasksDueToday: function() {
    return this._tasksDueToday.map(this.findTaskById, this);
  },

  findTaskById: function(taskId) {
    var tasks, projectName, task;
    tasks = [];
    for (projectName in this._projects) {
      tasks = tasks.concat((fn$.call(this)));
    }
    return tasks[0] || null;
    function fn$() {
      var i$, ref$, len$, results$ = [];
      for (i$ = 0, len$ = (ref$ = this._projects[projectName]).length; i$ < len$; ++i$) {
        task = ref$[i$];
        if (task.id === taskId) {
          results$.push(task);
        }
      }
      return results$;
    }
  },

  findAllProjects: function() {
    return Object.keys(this._projects);
  },

  findAllTasksForProject: function(projectName) {
    return this._projects[projectName] || null;
  }
};

module.exports = TaskService;

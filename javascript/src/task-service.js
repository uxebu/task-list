function TaskService(){
  this._projects = {};
}
var prototype = TaskService.prototype;
prototype._projects = null;
prototype._taskIdCounter = 0;
prototype.addProject = function(projectName){
  return this._projects[projectName] = [];
};
prototype.addTaskToProject = function(projectName, taskName){
  var task;
  if (!this._projects[projectName]) {
    throw Error;
  }
  task = this._createTask(taskName);
  this._projects[projectName].push(task);
  return task.id;
};
prototype._createTask = function(taskName){
  return {
    name: taskName,
    id: this._taskIdCounter++
  };
};
prototype.findTaskById = function(taskId){
  var tasks, projectName, task;
  tasks = [];
  for (projectName in this._projects) {
    tasks = tasks.concat((fn$.call(this)));
  }
  return tasks[0] || null;
  function fn$(){
    var i$, ref$, len$, results$ = [];
    for (i$ = 0, len$ = (ref$ = this._projects[projectName]).length; i$ < len$; ++i$) {
      task = ref$[i$];
      if (task.id === taskId) {
        results$.push(task);
      }
    }
    return results$;
  }
};
prototype.findAllProjects = function(){
  return Object.keys(this._projects);
};
prototype.findAllTasksForProject = function(projectName){
  return this._projects[projectName] || null;
};

module.exports = TaskService;

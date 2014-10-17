describe('tasks', function() {
  var tasks;
  beforeEach(function() {
    tasks = new Tasks();
  });
  it('no projects cant return any tasks', function() {
    expect(tasks.getAll()).toEqual([]);
  });
});

function Tasks() {}
Tasks.prototype = {
  getAll: function() {
    return [];
  }
};

// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://q13xxd2qz0.execute-api.us-east-2.amazonaws.com/beta/";

var list_url   = base_url + "listProjects";    // GET
var create_url = base_url + "createProject";    // POST
var delete_url = base_url + "deleteProject"; //POST
var add_task_url = base_url + "addTasks"; 
var add_mate_url = base_url + "addTeammates";
var remove_mate_url = base_url + "removeTeammates";//POST
var archive_url = base_url + "archiveProject";//POST
var mark_url = base_url + "markTask";
var rename_url = base_url+"renameTask";
var decompose_url = base_url+"decomposeTask";
var assign_url = base_url+"assignTeammatesToTask";
var unassign_url = base_url+"unassignTeammatesFromTask";

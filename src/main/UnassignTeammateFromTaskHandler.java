import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.ProjectsDAO;
import db.TasksDAO;
import db.TeammatesDAO;
import db.AssignmentsDAO;
import http.UnassignTeammateFromTaskRequest;
import http.UnassignTeammateFromTaskResponse;
import http.GenericErrorResponse;
import model.Project;
import model.Task;
import model.Teammate;
import model.Assignment;

public class UnassignTeammateFromTaskHandler implements RequestHandler<UnassignTeammateFromTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(UnassignTeammateFromTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		Object response = null;

		try {
			TasksDAO taskDao = new TasksDAO();
			TeammatesDAO teamDao = new TeammatesDAO();
			AssignmentsDAO assignDao = new AssignmentsDAO();

			List<Task> tasks = taskDao.getAllTasksByProject(req.projectName);
			List<Teammate> teammates = teamDao.getAllTeammates(req.projectName);

			Task task = taskDao.getTask(req.taskId);
			Teammate teammate = teamDao.getTeammate(req.teammateName, req.projectName);

			List<Assignment> assignments = assignDao.getAllAssignmentsByProject(req.projectName);
			Assignment assignment = assignDao.getAssignment(req.projectName, task.id, teammate.name);
			assignments.remove(assignment);

			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(req.projectName);
			project.tasks = tasks;
			project.teammates = teammates;

			if (!(project.isArchived)) {
				if (assignDao.removeAssignment(req.projectName, task.id, teammate.name)) {
					projDao.updateProject(project);
					List<Assignment> assignmentsUpdated = assignDao.getAllAssignmentsByProject(req.projectName);
					project.assignments = assignmentsUpdated;
					response = new UnassignTeammateFromTaskResponse(project, 200);
				} else {
					response = new UnassignTeammateFromTaskResponse(project, 422, "Unable to unassign teammate from task.");
				}
			} else {
				response = new UnassignTeammateFromTaskResponse(project, 422, "Unable to unassign teammate from task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to unassign teammate from task: " + req.teammateName + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

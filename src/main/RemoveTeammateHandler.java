import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.AssignmentsDAO;
import db.ProjectsDAO;
import db.TasksDAO;
import db.TeammatesDAO;
import http.DeleteProjectRequest;
import http.DeleteProjectResponse;
import http.GenericErrorResponse;
import http.RemoveTeammateRequest;
import http.RemoveTeammateResponse;
import model.Assignment;
import model.Project;
import model.Task;
import model.Teammate;

public class RemoveTeammateHandler implements RequestHandler<RemoveTeammateRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(RemoveTeammateRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to remove");

		Object response = null;
		logger.log(req.toString());

		Teammate teammate = new Teammate(req.name, req.projectName);

		try {
			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(req.projectName);

			TeammatesDAO teamDao = new TeammatesDAO();
			List<Teammate> originalTeam = teamDao.getAllTeammates(req.projectName);
			project.teammates = originalTeam;

			TasksDAO taskDao = new TasksDAO();
			List<Task> tasks = taskDao.getAllTasksByProject(req.projectName);
			project.tasks = tasks;

			AssignmentsDAO assignDao = new AssignmentsDAO();
			List<Assignment> assignments = assignDao.getAllAssignmentsByProject(req.projectName);

			for(Assignment assignment : assignments) {
				System.out.println("Here1");
				if(assignment.teammate.name.equals(req.name)) {
					System.out.println("Here2");
					assignDao.removeAssignment(req.projectName, assignment.task.id, req.name);
				}
			}

			if (!(project.isArchived) && teamDao.removeTeammate(teammate)) {
				List<Teammate> updatedMateList = teamDao.getAllTeammates(req.projectName);
				project.teammates = updatedMateList;
				List<Assignment> assignmentsUpdated = assignDao.getAllAssignmentsByProject(req.projectName);
				project.assignments = assignmentsUpdated;
				response = new RemoveTeammateResponse(project, 200);
			} else {
				response = new RemoveTeammateResponse(project, 422, "Unable to remove teammate.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to remove teammate: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

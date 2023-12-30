package edu.wpi.cs.heineman.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.db.AssignmentsDAO;
import edu.wpi.cs.heineman.demo.http.AssignTeammateToTaskRequest;
import edu.wpi.cs.heineman.demo.http.AssignTeammateToTaskResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;
import edu.wpi.cs.heineman.demo.model.Assignment;

public class AssignTeammateToTaskHandler implements RequestHandler<AssignTeammateToTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(AssignTeammateToTaskRequest req, Context context) {
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
			Assignment assignment = new Assignment(req.projectName, task, teammate);
			assignments.add(assignment);

			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(req.projectName);
			project.tasks = tasks;
			project.teammates = teammates;

			if (!(project.isArchived)) {
				if (assignDao.addAssignment(req.projectName, task.id, teammate.name)) {
					projDao.updateProject(project);
					List<Assignment> assignmentsUpdated = assignDao.getAllAssignmentsByProject(req.projectName);
					project.assignments = assignmentsUpdated;
					response = new AssignTeammateToTaskResponse(project, 200);
				}
				else {
					response = new AssignTeammateToTaskResponse(project, 422, "Unable to assign teammate to task.");
				}
			}
			else {
				response = new AssignTeammateToTaskResponse(project, 422, "Unable to assign teammate to task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to assign teammate to task: " + req.teammateName + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

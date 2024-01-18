package arobgrab;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.db.TeammatesDAO;
import arobgrab.http.GenericErrorResponse;
import arobgrab.http.MarkTaskRequest;
import arobgrab.http.MarkTaskResponse;
import arobgrab.http.RenameTaskRequest;
import arobgrab.http.RenameTaskResponse;
import arobgrab.model.Project;
import arobgrab.model.Task;
import arobgrab.model.Teammate;

public class RenameTaskHandler implements RequestHandler<RenameTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(RenameTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		Object response = null;

		TasksDAO taskDao = new TasksDAO();
		Task task = null;
		try {
			task = taskDao.getTask(req.id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			ProjectsDAO projDao = new ProjectsDAO();
			List<Task> tasks = null;
			Project project = null;

			try {
				tasks = taskDao.getAllTasksByProject(task.projectName);
			} catch (Exception e) {
				response = new RenameTaskResponse(project, 422, "Unable to find task.");
				return response;
			}

			project = projDao.getProject(task.projectName);
			project.tasks = tasks;

			TeammatesDAO teamDao = new TeammatesDAO();
			List<Teammate> teammates = teamDao.getAllTeammates(task.projectName);
			project.teammates = teammates;

			if (!(project.isArchived)) {
				if (taskDao.renameTask(task, req.newName)) {
					List<Task> tasksUpdated = taskDao.getAllTasksByProject(task.projectName);
					project.tasks = tasksUpdated;
					response = new RenameTaskResponse(project, 200);
				}
				else {
					response = new RenameTaskResponse(project, 422, "Unable to rename task.");
				}
			}
			else {
				response = new RenameTaskResponse(project, 422, "Unable to rename task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to rename task: " + task + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

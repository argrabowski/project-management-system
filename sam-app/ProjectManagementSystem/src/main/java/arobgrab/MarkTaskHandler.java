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
import arobgrab.model.Project;
import arobgrab.model.Task;
import arobgrab.model.Teammate;

public class MarkTaskHandler implements RequestHandler<MarkTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(MarkTaskRequest req, Context context) {
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
			List<Task> tasks = taskDao.getAllTasksByProject(task.projectName);

			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(task.projectName);
			project.tasks = tasks;

			TeammatesDAO teamDao = new TeammatesDAO();
			List<Teammate> teammates = teamDao.getAllTeammates(task.projectName);
			project.teammates = teammates;

			if (!(project.isArchived) && !(task.hasSubtasks)) {
				if (taskDao.markTask(task)) {
					projDao.updateProject(project);
					List<Task> tasksUpdated = taskDao.getAllTasksByProject(task.projectName);
					project.tasks = tasksUpdated;
					response = new MarkTaskResponse(project, 200);
				}
				else {
					response = new MarkTaskResponse(project, 422, "Unable to mark task.");
				}
			} else {
				response = new MarkTaskResponse(project, 422, "Unable to mark task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to mark task: " + task + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

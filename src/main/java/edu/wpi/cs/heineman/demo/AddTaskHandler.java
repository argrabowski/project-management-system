package edu.wpi.cs.heineman.demo;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.db.AssignmentsDAO;
import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.AddTaskRequest;
import edu.wpi.cs.heineman.demo.http.AddTaskResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Assignment;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

public class AddTaskHandler implements RequestHandler<AddTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(AddTaskRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());
		Object response = null;

		try {
			TasksDAO taskDao = new TasksDAO();
			UUID pID = UUID.randomUUID();
			String id = pID.toString();
			List<Task> tasks = taskDao.getAllTasksByProject(req.projectName);
			
			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(req.projectName);
			
			int tasksSize = tasks.size();
			for(Task task : tasks)
			{
				if(task.outlineIdentifier.contains("."))
				{
					tasksSize--;
				}
			}
			
			String outlineIdentifier = String.valueOf(tasksSize + 1);
			
			Task task = new Task (req.taskName, req.projectName, outlineIdentifier, id, false, false);
			
			TeammatesDAO teamDao = new TeammatesDAO();
			List<Teammate> teammates = teamDao.getAllTeammates(req.projectName);
			project.teammates = teammates;
			
			AssignmentsDAO assignDao = new AssignmentsDAO();
			List<Assignment> assignments = assignDao.getAllAssignmentsByProject(req.projectName);
			project.assignments = assignments;
			
			if (req.taskName.equals("")) {
				List<Task> tasksUpdated = taskDao.getAllTasksByProject(req.projectName);
				project.tasks = tasksUpdated;
				response = new AddTaskResponse(project, 200);
			}
			else if (!(project.isArchived)) {
				if (taskDao.addTask(task)) {
					projDao.updateProject(project);
					List<Task> tasksUpdated = taskDao.getAllTasksByProject(req.projectName);
					project.tasks = tasksUpdated;
					response = new AddTaskResponse(project, 200);
				}
				else {
					response = new AddTaskResponse(project, 422, "Unable to add task.");
				}
			}
			else {
				response = new AddTaskResponse(project, 422, "Unable to add task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to add task: " + req.taskName + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

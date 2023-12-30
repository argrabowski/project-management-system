package edu.wpi.cs.heineman.demo;

import java.util.ArrayList;
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
import edu.wpi.cs.heineman.demo.http.DecomposeTaskRequest;
import edu.wpi.cs.heineman.demo.http.DecomposeTaskResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Assignment;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

public class DecomposeTaskHandler implements RequestHandler<DecomposeTaskRequest,Object> {
	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(DecomposeTaskRequest req, Context context) {
		logger = context.getLogger();
//		logger.log(req.toString());
		Object response = null;

		try {
			char[] subtaskChars = req.subtaskNames.toCharArray();
			ArrayList<String> subtaskWords = new ArrayList<String>();
			char[] wordChars = new char[subtaskChars.length];
			for(int i = 0; i < req.subtaskNames.length(); i++) {
				if (subtaskChars[i] != ',') {
					wordChars[i] = subtaskChars[i];
				}
				if (subtaskChars[i] == ',' || (i == (req.subtaskNames.length() - 1))) {
					String word = new String(wordChars);
					word = word.trim();
					subtaskWords.add(word);
					char[] blankarray = new char[subtaskChars.length];
					wordChars = blankarray;
				}
//				if (i == (req.subtaskNames.length() - 1)) {
//					String word = new String(wordChars);
//					word = word.trim();
////					subtaskWords[j] = word;
//					subtaskWords.set(j, word);
//				}
			}
			
			TasksDAO taskDao = new TasksDAO();
			Task task = taskDao.getTask(req.id);
			String projectName = task.projectName;
			
			ArrayList<String> teammateIDs;
			teammateIDs = task.teammateIDs;
			task.teammateIDs = new ArrayList<String>();
			
			ArrayList<Task> tasks = taskDao.getAllTasksByProject(projectName);
			ArrayList<Task> subTasks = new ArrayList<Task>();
			for (int i = 0; i < subtaskWords.size(); i++) {
				String outlineIdentifier = task.outlineIdentifier + "." + (i + 1);
				UUID pID = UUID.randomUUID();
				String id = pID.toString();
				Task subTask = new Task (subtaskWords.get(i), projectName, outlineIdentifier, id, false, false);

				subTask.isMarked = false;
				int roundRobin;
				if (teammateIDs != null) {
					roundRobin = i % teammateIDs.size();
					subTask.teammateIDs.add(teammateIDs.get(roundRobin));
				}
				subTasks.add(subTask);
			}
			
			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(projectName);
			
			/* REASSING STUFF */
			AssignmentsDAO assignDao = new AssignmentsDAO();
			List<Assignment> assignments = assignDao.getAllAssignmentsByProject(projectName);
			List<Teammate> reassignMates = new ArrayList<Teammate>();
			
			for(Assignment assignment : assignments) {
				if(assignment.task.id.equals(req.id)) {
					assignDao.removeAssignment(projectName, req.id, assignment.teammate.name);
					reassignMates.add(assignment.teammate);
				}
			}
			/* REASSING STUFF */

			boolean addSuccess = true;
			for (Task subtask : subTasks) {
				if (req.subtaskNames.equals("")) {
					project.tasks = tasks;
					List<Assignment> assignmentsUpdated = assignDao.getAllAssignmentsByProject(projectName);
					project.assignments = assignmentsUpdated;
					response = new DecomposeTaskResponse(project, 200);
					return response;
				}
				else if (!(task.isMarked) && !(project.isArchived) && addSuccess && !(task.hasSubtasks)) {
					if (taskDao.addTask(subtask)) {
						taskDao.subtaskifyTask(task);
					}
					else {
						addSuccess = false;
					}
				} else {
					addSuccess = false;
				}
			}
			
			/* REASSING STUFF */
			int assignIndex = 0;
			for(Teammate reassignMate : reassignMates) {
				Task subtaskToAssign = subTasks.get(assignIndex);
				assignDao.addAssignment(projectName, subtaskToAssign.id, reassignMate.name);
				assignIndex++;
				if(assignIndex >= subTasks.size()) {
					assignIndex = 0;
				}
			}
			/* REASSING STUFF */
			
			if (addSuccess) {
				task = taskDao.getTask(task.id);
				List<Task> tasksUpdated = taskDao.getAllTasksByProject(projectName);
				int taskIndex = 0;
				int iterCounter = 0;
				for (Task taskIter : tasksUpdated) {
					if (taskIter.id.equals(task.id)) {
						taskIndex = iterCounter;
					}
					iterCounter++;
				}
				project.tasks = tasksUpdated;
				project.tasks.set(taskIndex, task);
				project.completionStatus = projDao.updateProject(project).completionStatus;
				List<Assignment> assignmentsUpdated = assignDao.getAllAssignmentsByProject(projectName);
				project.assignments = assignmentsUpdated;
				response = new DecomposeTaskResponse(project, 200);
			}
			else {
				response = new DecomposeTaskResponse(project, 422, "Unable to decompose task.");
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to decompose task: " + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

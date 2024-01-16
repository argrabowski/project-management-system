package arobgrab;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import arobgrab.db.AssignmentsDAO;
import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.db.TeammatesDAO;
import arobgrab.http.AddTeammateRequest;
import arobgrab.http.AddTeammateResponse;
import arobgrab.http.GenericErrorResponse;
import arobgrab.model.Assignment;
import arobgrab.model.Project;
import arobgrab.model.Task;
import arobgrab.model.Teammate;

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, Object> {
	LambdaLogger logger;
	private AmazonS3 s3 = null;

	boolean addTeammate(String name, String projectName) throws Exception { 
		if (logger != null) { logger.log("in addTeammate"); }
		TeammatesDAO dao = new TeammatesDAO();

		Teammate exist = dao.getTeammate(name, projectName);
		Teammate teammate = new Teammate (name, projectName);

		int canAdd = 1;
		if (exist != null) {
			canAdd = 0;
		} else {
			List<Teammate> allTeammates = dao.getAllTeammates(projectName);
			for (Teammate mate : allTeammates) {
				if (mate.name + mate.projectName == name + projectName) {
					canAdd = 0;
				}
			}
		}
		if (canAdd == 1) {
			return dao.addTeammate(teammate);
		} else {
			return false;
		}
	}

	@Override 
	public Object handleRequest(AddTeammateRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		Object response;
		try {
			TeammatesDAO teamDao = new TeammatesDAO();
			List<Teammate> teammates = teamDao.getAllTeammates(req.projectName);

			ProjectsDAO projDao = new ProjectsDAO();
			Project project = projDao.getProject(req.projectName);
			project.teammates = teammates;

			TasksDAO taskDao = new TasksDAO();
			List<Task> tasks = taskDao.getAllTasksByProject(req.projectName);
			project.tasks = tasks;

			AssignmentsDAO assignDao = new AssignmentsDAO();
			List<Assignment> assignments = assignDao.getAllAssignmentsByProject(req.projectName);
			project.assignments = assignments;

			if(req.name.equals("")) {
				response = new AddTeammateResponse(project);
			}
			else if (!(project.isArchived) && addTeammate(req.name, req.projectName)) {
				List<Teammate> teammatesUpdated = teamDao.getAllTeammates(req.projectName);
				project.teammates = teammatesUpdated;
				response = new AddTeammateResponse(project);
			}
			else {
				response = new AddTeammateResponse(project, 422);
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to add teammate: " + req.name + ", " + req.projectName + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

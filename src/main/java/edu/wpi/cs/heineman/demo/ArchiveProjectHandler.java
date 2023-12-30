package edu.wpi.cs.heineman.demo;

import java.util.List;

import java.util.LinkedList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.ArchiveProjectRequest;
import edu.wpi.cs.heineman.demo.http.ArchiveProjectResponse;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;
import edu.wpi.cs.heineman.demo.http.DeleteProjectRequest;
import edu.wpi.cs.heineman.demo.http.DeleteProjectResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Teammate;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;

/**
 * No more JSON parsing
 */
public class ArchiveProjectHandler implements RequestHandler<ArchiveProjectRequest,Object> {

	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(ArchiveProjectRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to archive");

		Object response = null;
		logger.log(req.toString());

		ProjectsDAO dao = new ProjectsDAO();

		Project project = new Project(req.name, true);
		try {
			if (dao.archiveProject(project)) {
				project = dao.getProject(req.name);
				response = new ArchiveProjectResponse(project, 200);
			}
			else {
				response = new ArchiveProjectResponse(project, 422, "Unable to archive project: " + req.name);
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to archive project: " + req.name + "(" + e.getMessage() + ")");
		}
		return response;
	}
}

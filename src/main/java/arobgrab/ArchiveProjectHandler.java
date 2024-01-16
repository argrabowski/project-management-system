package arobgrab;

import java.util.List;
import java.util.LinkedList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.db.TeammatesDAO;
import arobgrab.http.ArchiveProjectRequest;
import arobgrab.http.ArchiveProjectResponse;
import arobgrab.http.CreateProjectResponse;
import arobgrab.http.DeleteProjectRequest;
import arobgrab.http.DeleteProjectResponse;
import arobgrab.http.GenericErrorResponse;
import arobgrab.model.Teammate;
import arobgrab.model.Project;
import arobgrab.model.Task;

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

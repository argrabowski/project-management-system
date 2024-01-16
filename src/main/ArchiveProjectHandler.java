import java.util.List;
import java.util.LinkedList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import db.ProjectsDAO;
import db.TasksDAO;
import db.TeammatesDAO;
import http.ArchiveProjectRequest;
import http.ArchiveProjectResponse;
import http.CreateProjectResponse;
import http.DeleteProjectRequest;
import http.DeleteProjectResponse;
import http.GenericErrorResponse;
import model.Teammate;
import model.Project;
import model.Task;

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

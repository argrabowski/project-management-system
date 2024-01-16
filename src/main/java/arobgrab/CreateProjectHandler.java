package arobgrab;

import java.io.ByteArrayInputStream;
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

import arobgrab.db.ProjectsDAO;
import arobgrab.http.GenericErrorResponse;
import arobgrab.model.Project;
import arobgrab.http.CreateProjectRequest;
import arobgrab.http.CreateProjectResponse;

public class CreateProjectHandler implements RequestHandler<CreateProjectRequest, Object> {
	LambdaLogger logger;
	private AmazonS3 s3 = null;

	public boolean createProject(String name) throws Exception { 
		if (logger != null) { logger.log("in createProject"); }
		ProjectsDAO dao = new ProjectsDAO();

		Project exist = dao.getProject(name);
		UUID pID = UUID.randomUUID();
		String id = pID.toString();
		Project project = new Project (name, id);

		if (exist == null) {
			return dao.createProject(project);
		} else {
			return false;
		}
	}

	@Override 
	public Object handleRequest(CreateProjectRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		Object response;
		try {
			if (createProject(req.name)) {
				response = new CreateProjectResponse(req.name, 200);
			} else {
				response = new CreateProjectResponse(req.name, 422);
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to create project: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
}

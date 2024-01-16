import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import db.ProjectsDAO;
import http.listAllProjectsResponse;
import model.Project;

public class ListAllProjectsHandler implements RequestHandler<Object,listAllProjectsResponse> {
	public LambdaLogger logger;

	List<Project> getProjects() throws Exception {
		logger.log("in getProjects");
		ProjectsDAO dao = new ProjectsDAO();
		
		return dao.getAllProjects();
	}

	@Override
	public listAllProjectsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all projects");

		listAllProjectsResponse response;
		try {
			List<Project> list = getProjects();
			response = new listAllProjectsResponse(list, 200);
		} catch (Exception e) {
			response = new listAllProjectsResponse(403, e.getMessage());
		}

		return response;
	}
}

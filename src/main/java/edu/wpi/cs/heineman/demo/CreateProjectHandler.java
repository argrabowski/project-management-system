package edu.wpi.cs.heineman.demo;

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

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.http.CreateProjectRequest;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;


// Create a new constant and store in S3 bucket.

public class CreateProjectHandler implements RequestHandler<CreateProjectRequest, Object> {

	LambdaLogger logger;
	
	// To access S3 storage
	private AmazonS3 s3 = null;
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	//public static final String BUCKET = "projects/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	public boolean createProject(String name) throws Exception { 
		if (logger != null) { logger.log("in createProject"); }
		ProjectsDAO dao = new ProjectsDAO();
		
		// check if present
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
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	/*
	boolean createSystemProject(String name, String id) throws Exception {
		if (logger != null) { logger.log("in createSystemProject"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}

		String bucket = BUCKET;
		
		byte[] contents = ("" + id).getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest("3733projectappstabit", bucket + name, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}*/
	
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

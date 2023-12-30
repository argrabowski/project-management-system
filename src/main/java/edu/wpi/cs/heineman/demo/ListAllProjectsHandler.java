package edu.wpi.cs.heineman.demo;

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

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.http.listAllProjectsResponse;
import edu.wpi.cs.heineman.demo.model.Project;

/**
 * Eliminated need to work with JSON
 */
public class ListAllProjectsHandler implements RequestHandler<Object,listAllProjectsResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	public static final String REAL_BUCKET = "projects";

	public static final String TOP_LEVEL_BUCKET = "3733projectappstabit";
	
	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Project> getProjects() throws Exception {
		logger.log("in getProjects");
		ProjectsDAO dao = new ProjectsDAO();
		
		return dao.getAllProjects();
	}
	
	// I am leaving in this S3 code so it can be a LAST RESORT if the constant is not in the database
	private AmazonS3 s3 = null;
	
	/**
	 * Retrieve all SYSTEM constants. This code is surprisingly dangerous since there could
	 * be an incredible number of objects in the bucket. Ignoring this for now.
	 */
	/*
	List<Project> systemProjects() throws Exception {
		logger.log("in systemProjects");
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		ArrayList<Project> sysProjects = new ArrayList<>();
	    
		String bucket = REAL_BUCKET;
		// retrieve listing of all objects in the designated bucket
		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
				  .withBucketName(TOP_LEVEL_BUCKET)    // top-level bucket
				  .withPrefix(bucket);            		  // sub-folder declarations here (i.e., a/b/c)
												  
		
		// request the s3 objects in the s3 bucket 'cs3733wpi/constants' -- change based on your bucket name
		logger.log("process request");
		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
		logger.log("process request succeeded");
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		
		for (S3ObjectSummary os: objects) {
	      String name = os.getKey();
		  logger.log("S3 found:" + name);

	      // If name ends with slash it is the 'constants/' bucket itself so you skip
	      if (name.endsWith("/")) { continue; }
			
	      S3Object obj = s3.getObject(TOP_LEVEL_BUCKET, name);
	    	
	    	try (S3ObjectInputStream projectStream = obj.getObjectContent()) {
				Scanner sc = new Scanner(projectStream);
				String val = sc.nextLine();
				sc.close();
				
				// just grab name *after* the slash. Note this is a SYSTEM constant
				int postSlash = name.indexOf('/');
				sysProjects.add(new Project(name.substring(postSlash+1), String.valueOf(val), true));
			} catch (Exception e) {
				logger.log("Unable to parse contents of " + name);
			}
	    }
		
		return sysProjects;
	}*/
	
	@Override
	public listAllProjectsResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all projects");

		listAllProjectsResponse response;
		try {
			List<Project> list = getProjects();
			/*
			for (Project c : systemProjects()) {
				if (!list.contains(c)) {
					list.add(c);
				}
			}*/
			response = new listAllProjectsResponse(list, 200);
		} catch (Exception e) {
			response = new listAllProjectsResponse(403, e.getMessage());
		}
		
		return response;
	}
}

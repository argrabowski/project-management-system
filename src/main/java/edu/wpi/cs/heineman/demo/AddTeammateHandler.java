package edu.wpi.cs.heineman.demo;

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

import edu.wpi.cs.heineman.demo.db.AssignmentsDAO;
import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Assignment;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;
import edu.wpi.cs.heineman.demo.http.AddTeammateRequest;
import edu.wpi.cs.heineman.demo.http.AddTeammateResponse;


// Create a new constant and store in S3 bucket.

public class AddTeammateHandler implements RequestHandler<AddTeammateRequest, Object> {

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
	boolean addTeammate(String name, String projectName) throws Exception { 
		if (logger != null) { logger.log("in addTeammate"); }
		TeammatesDAO dao = new TeammatesDAO();
		
		// check if present
		Teammate exist = dao.getTeammate(name, projectName);
		Teammate teammate = new Teammate (name, projectName);
		
		// the logic below ensures a name and project name, when concatenated, don't equal the same string
		// example: name + proj = nameproj		nam + eproj = nameproj
		// thus, the response strings would be the same for that teammate, which we can't allow
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

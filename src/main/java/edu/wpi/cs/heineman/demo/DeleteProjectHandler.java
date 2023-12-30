package edu.wpi.cs.heineman.demo;

import java.util.List;

import java.util.LinkedList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.DeleteProjectRequest;
import edu.wpi.cs.heineman.demo.http.DeleteProjectResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;
import edu.wpi.cs.heineman.demo.model.Teammate;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;

/**
 * No more JSON parsing
 */
public class DeleteProjectHandler implements RequestHandler<DeleteProjectRequest,Object> {

	public LambdaLogger logger = null;

	@Override
	public Object handleRequest(DeleteProjectRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		Object response = null;
		logger.log(req.toString());

		ProjectsDAO dao = new ProjectsDAO();
		
		TeammatesDAO teamdao = new TeammatesDAO();
		TasksDAO taskdao = new TasksDAO();

		// MAKE sure that we prevent attempts to delete system constants...
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		Project project = new Project(req.name, "");
		try {
			
			boolean allTeammatesRemoved = allTeammatesRemoved(teamdao, project);
			
			boolean allTasksRemoved = allTasksRemoved(taskdao, project);
			
			if (dao.deleteProject(project) && allTeammatesRemoved && allTasksRemoved) {
				response = new DeleteProjectResponse(req.name, 200);
			} else {
				if(!allTeammatesRemoved) {
					response = new DeleteProjectResponse(req.name, 422, "Unable to delete project. Not all temmates removed");
				}else if(!allTasksRemoved) {
					response = new DeleteProjectResponse(req.name, 422, "Unable to delete project. Not all tasks removed");
				}else if(!allTasksRemoved && !allTeammatesRemoved) {
					response = new DeleteProjectResponse(req.name, 422, "Unable to delete project. Not all tasks and teammates removed");
				}else {
					response = new DeleteProjectResponse(req.name, 422, "Unable to delete project.");
				}
			}
		} catch (Exception e) {
			response = new GenericErrorResponse(400, "Unable to delete project: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}
	
	public boolean allTeammatesRemoved(TeammatesDAO teamdao, Project project) throws Exception {
		boolean allTeammatesRemoved = false;
		
		List<Teammate> tlist = teamdao.getAllTeammates(project.name);
		
		if(tlist.size() == 0) {
			allTeammatesRemoved = true;
		}
		
		for(Teammate t: tlist) {
			System.out.println(t.name+" "+t.projectName);
			if(teamdao.removeTeammate(t)) {
				allTeammatesRemoved = true;
			}else {
				allTeammatesRemoved = false;
			}
		}
		
		return allTeammatesRemoved;
	}
	
	public boolean allTasksRemoved(TasksDAO taskdao, Project project) throws Exception {
		boolean allTasksRemoved = false;
		
		List<Task> tlist = taskdao.getAllTasksByProject(project.name);
		
		if(tlist.size() == 0) {
			allTasksRemoved = true;
		}
		
		for(Task t: tlist) {
			System.out.println(t.name+" "+t.projectName);
			if(taskdao.removeTask(t)) {
				allTasksRemoved = true;
			}else {
				allTasksRemoved = false;
			}
		}
		
		return allTasksRemoved;
	}
}

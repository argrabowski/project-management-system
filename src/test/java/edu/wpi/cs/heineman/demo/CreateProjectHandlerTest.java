package edu.wpi.cs.heineman.demo;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.http.CreateProjectRequest;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;
import edu.wpi.cs.heineman.demo.model.Project;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	CreateProjectHandler handler = new CreateProjectHandler();
    	CreateProjectRequest req = new Gson().fromJson(incoming,  CreateProjectRequest.class);
       
        CreateProjectResponse resp = (CreateProjectResponse) handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(200, resp.httpCode);
    }
	
    void testFailInput(String incoming) throws IOException {
    	CreateProjectHandler handler = new CreateProjectHandler();
    	CreateProjectRequest req = new Gson().fromJson(incoming, CreateProjectRequest.class);

    	CreateProjectResponse resp = (CreateProjectResponse) handler.handleRequest(req, createContext("create"));
        Assert.assertEquals(422, resp.httpCode);
    }
    
	static String var = "";
    @Test
    public void testShouldBeOk() {
    	
//    	String var = "coverage";
    	
        int rndNum = (int)(19900*(Math.random()));
    	String var = "throWayCoverage" + rndNum;
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(var);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    @Test
    public void testShouldFail() {
    	
    	ProjectsDAO dao = new ProjectsDAO();
    	List<Project> projects = null;
    	try {
			projects = dao.getAllProjects();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Project project = projects.get(0);
    	
    	CreateProjectRequest ccr = new CreateProjectRequest(project.name);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    
    /*
    @Test
    public void testGarbageInput() {
    	String SAMPLE_INPUT_STRING = "{\"sdsd\": \"proj3\", \"hgfgdfgdfg\": \"this is not a number\"}";
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING, 400);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }*/
    
    
    // overwrites into it
    @Test
    public void testCreateSystemProject() {
    	// create constant
    	int rndNum = (int)(19900*(Math.random()));
    	CreateProjectRequest csr = new CreateProjectRequest("to-delete-again-coverage"+rndNum);
    	CreateProjectHandler h = new CreateProjectHandler();
        
    	CreateProjectResponse resp = (CreateProjectResponse) h.handleRequest(csr, createContext("create"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.httpCode);
    }
}
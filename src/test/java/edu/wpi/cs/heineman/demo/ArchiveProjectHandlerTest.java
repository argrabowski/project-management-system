package edu.wpi.cs.heineman.demo;

import java.io.IOException;


import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.http.AddTeammateRequest;
import edu.wpi.cs.heineman.demo.http.ArchiveProjectRequest;
import edu.wpi.cs.heineman.demo.http.ArchiveProjectResponse;
import edu.wpi.cs.heineman.demo.http.CreateProjectRequest;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;
import edu.wpi.cs.heineman.demo.model.Project;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ArchiveProjectHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming,  ArchiveProjectRequest.class);
       
        ArchiveProjectResponse resp = (ArchiveProjectResponse) handler.handleRequest(req, createContext("archive"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);

    	ArchiveProjectResponse resp = (ArchiveProjectResponse) handler.handleRequest(req, createContext("archive"));
        Assert.assertEquals(422, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
    	
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now archive
        ArchiveProjectRequest dcr = new ArchiveProjectRequest("x" + rnd);
        ArchiveProjectResponse d_resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(dcr, createContext("archive"));
        Assert.assertEquals("x" + rnd, d_resp.project.name);
        
        ArchiveProjectRequest ccr2 = new ArchiveProjectRequest("x" + rnd);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr2);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
        
        // dcrBad is a request for a project that doesn't exist
        ArchiveProjectRequest dcrBad = new ArchiveProjectRequest("x" + rnd + rnd);
        // try again and this should fail
        d_resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(dcrBad, createContext("archive"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    @Test
    public void testShouldFail() {
    	
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        ArchiveProjectRequest ccr = new ArchiveProjectRequest("x" + rnd);
        
        ProjectsDAO projDao = new ProjectsDAO();
        Project project = null;
		try {
			project = projDao.getProject("x" + rnd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ArchiveProjectResponse resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(ccr, createContext("archive"));
        Assert.assertNull(project);
        
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
    
//    /*
    // overwrites into it
    @Test
    public void testArchiveSystemProject() {
    	
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
    	
    	// Archive Project
    	ArchiveProjectRequest csr = new ArchiveProjectRequest("x" + rnd);
    	ArchiveProjectHandler h = new ArchiveProjectHandler();
        
    	ArchiveProjectResponse Aresp = (ArchiveProjectResponse) h.handleRequest(csr, createContext("archive"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, Aresp.statusCode);
    }
//    */
}
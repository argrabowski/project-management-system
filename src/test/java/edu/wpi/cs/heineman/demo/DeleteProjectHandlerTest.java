package edu.wpi.cs.heineman.demo;

import org.junit.Assert;


import org.junit.Test;

import edu.wpi.cs.heineman.demo.http.CreateProjectRequest;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;
import edu.wpi.cs.heineman.demo.http.DeleteProjectRequest;
import edu.wpi.cs.heineman.demo.http.DeleteProjectResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteProjectHandlerTest extends LambdaTest {

    @Test
    public void testCreateAndDeleteproject() {
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);
        
        // try again and this should fail
        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
    @Test
    public void testCreateAndDeleteprojectWithTeammte() {
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);
        
        // try again and this should fail
        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
    @Test
    public void testCreateAndDeleteprojectWithMultTeammates() {
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);
        
        // try again and this should fail
        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
    @Test
    public void testCreateAndDeleteprojectWithMultTeammatesTasks() {
    	// create project
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);
        
        // now delete
        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);
        
        // try again and this should fail
        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
    
}

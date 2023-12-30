package edu.wpi.cs.heineman.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.AddTeammateRequest;
import edu.wpi.cs.heineman.demo.http.AddTeammateResponse;
import edu.wpi.cs.heineman.demo.http.CreateProjectRequest;
import edu.wpi.cs.heineman.demo.http.CreateProjectResponse;
import edu.wpi.cs.heineman.demo.http.DeleteProjectResponse;
import edu.wpi.cs.heineman.demo.http.RemoveTeammateRequest;
import edu.wpi.cs.heineman.demo.http.RemoveTeammateResponse;
import edu.wpi.cs.heineman.demo.model.Teammate;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RemoveTeammateHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();
    	RemoveTeammateRequest req = new Gson().fromJson(incoming,  RemoveTeammateRequest.class);
       
        RemoveTeammateResponse resp = (RemoveTeammateResponse) handler.handleRequest(req, createContext("remove"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();
    	RemoveTeammateRequest req = new Gson().fromJson(incoming, RemoveTeammateRequest.class);

    	RemoveTeammateResponse resp = (RemoveTeammateResponse) handler.handleRequest(req, createContext("remove"));
        Assert.assertEquals(422, resp.statusCode);
    }

   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() {
        
    	// add teammate
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;
    	
    	AddTeammateRequest ccr = new AddTeammateRequest(var, "coverage");
    	AddTeammateResponse resp = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr, createContext("add"));
        
    	TeammatesDAO mateDao = new TeammatesDAO();
    	String name = "";
		try {
			name = mateDao.getTeammate(var, resp.project.name).name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Assert.assertEquals(var, name);
    	
        // now remove teammate
    	RemoveTeammateRequest rcr = new RemoveTeammateRequest("coverage", var);  
    	RemoveTeammateResponse r_resp = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));
    	
    	Teammate teammate = null;
    	try {
			teammate = mateDao.getTeammate(var, r_resp.project.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Assert.assertNull(teammate);
    	
    	String var2 = "throwAwayCoverage" + rndNum;
    	
    	AddTeammateRequest ccr2 = new AddTeammateRequest(var2, "coverage");
    	AddTeammateResponse resp2 = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr2, createContext("add"));
    	
        String SAMPLE_INPUT_STRING = new Gson().toJson(rcr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
    @Test
    public void testShouldFail() {
        
    	// add teammate
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;
    	
    	AddTeammateRequest ccr = new AddTeammateRequest(var, "coverage");
    	AddTeammateResponse resp = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr, createContext("add"));
        
    	TeammatesDAO mateDao = new TeammatesDAO();
    	String name = "";
		try {
			name = mateDao.getTeammate(var, resp.project.name).name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Assert.assertEquals(var, name);
    	
        // now remove teammate
    	RemoveTeammateRequest rcr = new RemoveTeammateRequest("coverage", var);  
    	RemoveTeammateResponse r_resp = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));
    	
    	Teammate teammate = null;
    	try {
			teammate = mateDao.getTeammate(var, r_resp.project.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Assert.assertNull(teammate);
    	
    	RemoveTeammateRequest rcr2 = new RemoveTeammateRequest("coverage", var);  
    	RemoveTeammateResponse r_resp2 = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));
    	
    	String SAMPLE_INPUT_STRING = new Gson().toJson(rcr2);  
        
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
    
    
    /*
    // overwrites into it
    @Test
    public void testCreateSystemProject() {
    	// create constant
    	int rndNum = (int)(990*(Math.random()));
    	RemoveTeammateRequest csr = new RemoveTeammateRequest("to-delete-again"+rndNum, "coverage");
    	RemoveTeammateHandler h = new RemoveTeammateHandler();
        
    	RemoveTeammateResponse resp = (RemoveTeammateResponse) h.handleRequest(csr, createContext("remove"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
    */
}

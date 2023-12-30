package edu.wpi.cs.heineman.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.http.AddTaskRequest;
import edu.wpi.cs.heineman.demo.http.AddTaskResponse;
import edu.wpi.cs.heineman.demo.http.AddTeammateRequest;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	AddTaskHandler handler = new AddTaskHandler();
    	AddTaskRequest req = new Gson().fromJson(incoming,  AddTaskRequest.class);
       
        AddTaskResponse resp = (AddTaskResponse) handler.handleRequest(req, createContext("add"));
        Assert.assertEquals(200, resp.statusCode);
    }
   
    // NOTE: this proliferates large number of constants! Be mindful
    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;
//    	String var = "task1";
    	
    	AddTaskRequest ccr = new AddTaskRequest(var, "coverage");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
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
    }
    */
    // overwrites into it
    @Test
    public void testCreateSystemProject() {
    	// create constant
    	int rndNum = (int)(990*(Math.random()));
    	AddTaskRequest csr = new AddTaskRequest("to-delete-again-coverage"+rndNum, "coverage");
    	AddTaskHandler h = new AddTaskHandler();
        
    	AddTaskResponse resp = (AddTaskResponse) h.handleRequest(csr, createContext("add"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
}

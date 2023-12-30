package edu.wpi.cs.heineman.demo;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.http.AssignTeammateToTaskRequest;
import edu.wpi.cs.heineman.demo.http.AssignTeammateToTaskResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AssignTeammateToTaskHandlerTest extends LambdaTest {

	@Test
    public void testAssignTeammateToTask() {
    	AssignTeammateToTaskHandler h = new AssignTeammateToTaskHandler();
    	AssignTeammateToTaskRequest req = new AssignTeammateToTaskRequest();
    	req.teammateName = "Adam";
    	req.taskId = "1000";
    	req.projectName = "coverage";
    	
    	GenericErrorResponse resp = (GenericErrorResponse) h.handleRequest(req, createContext("assign"));
 
        Assert.assertEquals(400, resp.statusCode);
    }
}

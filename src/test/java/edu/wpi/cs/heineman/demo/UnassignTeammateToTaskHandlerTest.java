package edu.wpi.cs.heineman.demo;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.http.UnassignTeammateFromTaskRequest;
import edu.wpi.cs.heineman.demo.http.UnassignTeammateFromTaskResponse;
import edu.wpi.cs.heineman.demo.http.GenericErrorResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UnassignTeammateToTaskHandlerTest extends LambdaTest {

	@Test
    public void testUnassignTeammateToTask() {
    	UnassignTeammateFromTaskHandler h = new UnassignTeammateFromTaskHandler();
    	UnassignTeammateFromTaskRequest req = new UnassignTeammateFromTaskRequest();
    	req.teammateName = "Adam";
    	req.taskId = "1000";
    	req.projectName = "coverage";
    	
    	GenericErrorResponse resp = (GenericErrorResponse) h.handleRequest(req, createContext("assign"));
 
        Assert.assertEquals(400, resp.statusCode);
    }
}

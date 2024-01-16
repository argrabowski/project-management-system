package arobgrab;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import arobgrab.http.AssignTeammateToTaskRequest;
import arobgrab.http.AssignTeammateToTaskResponse;
import arobgrab.http.GenericErrorResponse;

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

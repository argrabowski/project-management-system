package arobgrab;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.db.TeammatesDAO;
import arobgrab.http.AddTaskRequest;
import arobgrab.http.AddTaskResponse;
import arobgrab.http.AddTeammateRequest;
import arobgrab.model.Project;
import arobgrab.model.Task;
import arobgrab.model.Teammate;

public class AddTaskHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	AddTaskHandler handler = new AddTaskHandler();
    	AddTaskRequest req = new Gson().fromJson(incoming, AddTaskRequest.class);

        AddTaskResponse resp = (AddTaskResponse) handler.handleRequest(req, createContext("add"));
        Assert.assertEquals(200, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;

    	AddTaskRequest ccr = new AddTaskRequest(var, "coverage");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);

        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testCreateSystemProject() {
    	int rndNum = (int)(990*(Math.random()));
    	AddTaskRequest csr = new AddTaskRequest("to-delete-again-coverage"+rndNum, "coverage");
    	AddTaskHandler h = new AddTaskHandler();

    	AddTaskResponse resp = (AddTaskResponse) h.handleRequest(csr, createContext("add"));

        Assert.assertEquals(200, resp.statusCode);
    }
}

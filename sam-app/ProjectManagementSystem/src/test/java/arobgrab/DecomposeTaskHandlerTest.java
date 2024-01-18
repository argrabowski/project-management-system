package arobgrab;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.http.DecomposeTaskRequest;
import arobgrab.http.DecomposeTaskResponse;
import arobgrab.model.Project;
import arobgrab.model.Task;

public class DecomposeTaskHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	DecomposeTaskHandler handler = new DecomposeTaskHandler();
    	DecomposeTaskRequest req = new Gson().fromJson(incoming,  DecomposeTaskRequest.class);

        DecomposeTaskResponse resp = (DecomposeTaskResponse) handler.handleRequest(req, createContext("Decompose"));
		Assert.assertEquals(200, resp.statusCode);
    }

    void testFailInput(String incoming) throws IOException {
    	DecomposeTaskHandler handler = new DecomposeTaskHandler();
    	DecomposeTaskRequest req = new Gson().fromJson(incoming, DecomposeTaskRequest.class);

    	DecomposeTaskResponse resp = (DecomposeTaskResponse) handler.handleRequest(req, createContext("Decompose"));
        Assert.assertEquals(422, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
    	TasksDAO taskDao = new TasksDAO();
    	ArrayList<Task> allTasks = new ArrayList<>();

    	try {
			allTasks = taskDao.getAllTasksByProject("coverage");
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ArrayList<Task> allSubtasks = new ArrayList<>();
    	for (Task task : allTasks) {
    		if (!task.hasSubtasks) {
    			allSubtasks.add(task);
    		}
    	}

    	int rndNum = (int)(990*(Math.random())) % allSubtasks.size() - 1;
    	String var = allSubtasks.get(rndNum).id;

    	DecomposeTaskRequest ccr = new DecomposeTaskRequest(var, "subsubtask1,subsubtask2,subsubtask3,subsubtask4");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);

        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testShouldFail() {
    	TasksDAO taskDao = new TasksDAO();
    	ArrayList<Task> allTasks = new ArrayList<>();

    	try {
			allTasks = taskDao.getAllTasksByProject("coverage");
		} catch (Exception e) {
			e.printStackTrace();
		}

    	ArrayList<Task> allNonSubtasks = new ArrayList<>();
    	for (Task task : allTasks) {
    		if (task.hasSubtasks) {
    			allNonSubtasks.add(task);
    		}
    	}

    	int rndNum = (int)(990*(Math.random())) % allNonSubtasks.size() - 1;
    	String var = allNonSubtasks.get(rndNum).id;

    	DecomposeTaskRequest ccr = new DecomposeTaskRequest(var, "subsubtask1, subsubtask2,subsubtask3,    subsubtask4");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);

        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}

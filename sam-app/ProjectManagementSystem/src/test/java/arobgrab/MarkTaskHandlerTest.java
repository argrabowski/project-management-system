package arobgrab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import arobgrab.db.ProjectsDAO;
import arobgrab.db.TasksDAO;
import arobgrab.http.DecomposeTaskRequest;
import arobgrab.http.DecomposeTaskResponse;
import arobgrab.http.MarkTaskRequest;
import arobgrab.http.MarkTaskResponse;
import arobgrab.model.Task;

public class MarkTaskHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	MarkTaskHandler handler = new MarkTaskHandler();
    	MarkTaskRequest req = new Gson().fromJson(incoming,  MarkTaskRequest.class);

        MarkTaskResponse resp = (MarkTaskResponse) handler.handleRequest(req, createContext("mark"));
        Assert.assertEquals(200, resp.statusCode);
    }

    void testFailInput(String incoming) throws IOException {
    	MarkTaskHandler handler = new MarkTaskHandler();
    	MarkTaskRequest req = new Gson().fromJson(incoming, MarkTaskRequest.class);

    	MarkTaskResponse resp = (MarkTaskResponse) handler.handleRequest(req, createContext("mark"));
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
    	MarkTaskRequest ccr = new MarkTaskRequest(var);

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
    	ProjectsDAO projDao = new ProjectsDAO();

    	ArrayList<Task> allTasks = new ArrayList<Task>();
		try {
			allTasks = taskDao.getAllTasksByProject("coverage");
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		DecomposeTaskRequest dcr = new DecomposeTaskRequest(allTasks.get(0).id, "subtask1, subtask2");
		DecomposeTaskHandler h = new DecomposeTaskHandler();
    	DecomposeTaskResponse d_resp = (DecomposeTaskResponse) h.handleRequest(dcr, createContext("decompose"));

    	MarkTaskRequest mcr = new MarkTaskRequest(allTasks.get(0).id);
    	MarkTaskHandler mh = new MarkTaskHandler();
    	MarkTaskResponse m_resp = (MarkTaskResponse) mh.handleRequest(mcr, createContext("mark"));

    	String SAMPLE_INPUT_STRING = new Gson().toJson(mcr);  

        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testCreateSystemProject() {
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

    	MarkTaskRequest csr = new MarkTaskRequest(var);
    	MarkTaskHandler h = new MarkTaskHandler();

    	MarkTaskResponse resp = (MarkTaskResponse) h.handleRequest(csr, createContext("mark"));

        Assert.assertEquals(200, resp.statusCode);
    }
}

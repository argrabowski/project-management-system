package edu.wpi.cs.heineman.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.http.DecomposeTaskRequest;
import edu.wpi.cs.heineman.demo.http.DecomposeTaskResponse;
import edu.wpi.cs.heineman.demo.model.Project;
import edu.wpi.cs.heineman.demo.model.Task;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DecomposeTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	DecomposeTaskHandler handler = new DecomposeTaskHandler();
    	DecomposeTaskRequest req = new Gson().fromJson(incoming,  DecomposeTaskRequest.class);
       
        DecomposeTaskResponse resp = (DecomposeTaskResponse) handler.handleRequest(req, createContext("Decompose"));
//        Assert.assertEquals(200, resp.statusCode);
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
			// TODO Auto-generated catch block
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
//    	String var = "735d2750-5101-4a9b-a62b-42d8b0505398";
    	
    	DecomposeTaskRequest ccr = new DecomposeTaskRequest(var, "subsubtask1, subsubtask2,subsubtask3,    subsubtask4");
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
			// TODO Auto-generated catch block
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
    
    /*
    // overwrites into it
    @Test
    public void testCreateSystemProject() {
    	TasksDAO taskDao = new TasksDAO();
    	ArrayList<Task> allTasks = new ArrayList<>();
    	try {
			allTasks = taskDao.getAllTasksByProject("coverage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
    	DecomposeTaskRequest csr = new DecomposeTaskRequest(var, "coverage");
    	DecomposeTaskHandler h = new DecomposeTaskHandler();
        
    	DecomposeTaskResponse resp = (DecomposeTaskResponse) h.handleRequest(csr, createContext("Decompose"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
    */
}

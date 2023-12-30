package edu.wpi.cs.heineman.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.http.RenameTaskRequest;
import edu.wpi.cs.heineman.demo.http.RenameTaskResponse;
import edu.wpi.cs.heineman.demo.model.Task;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RenameTaskHandlerTest extends LambdaTest {

    void testSuccessInput(String incoming) throws IOException {
    	RenameTaskHandler handler = new RenameTaskHandler();
    	RenameTaskRequest req = new Gson().fromJson(incoming,  RenameTaskRequest.class);
       
        RenameTaskResponse resp = (RenameTaskResponse) handler.handleRequest(req, createContext("Rename"));
        Assert.assertEquals(200, resp.statusCode);
    }
	
    void testFailInput(String incoming) throws IOException {
    	RenameTaskHandler handler = new RenameTaskHandler();
    	RenameTaskRequest req = new Gson().fromJson(incoming, RenameTaskRequest.class);

    	RenameTaskResponse resp = (RenameTaskResponse) handler.handleRequest(req, createContext("Rename"));
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
    	String newName = allSubtasks.get(rndNum).name + "|";
//		String id = "ffccfc6c-33f7-43dd-8792-275b35dc5414";
    	RenameTaskRequest ccr = new RenameTaskRequest(var, newName);
    	
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
    	ArrayList<Task> allSubtasks = new ArrayList<>();
    	for (Task task : allTasks) {
    		if (!task.hasSubtasks) {
    			allSubtasks.add(task);
    		}
    	}
    	int rndNum = (int)(990*(Math.random())) % allSubtasks.size() - 1;
    	String var = allSubtasks.get(rndNum).id;
    	String newName = allSubtasks.get(rndNum).name + "|";
    	RenameTaskRequest ccr = new RenameTaskRequest(var + var, newName);
    	
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
    }*/
    
//    /*
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
    	String newName = allSubtasks.get(rndNum).name + "|";
		
    	RenameTaskRequest csr = new RenameTaskRequest(var, newName);
    	RenameTaskHandler h = new RenameTaskHandler();
        
    	RenameTaskResponse resp = (RenameTaskResponse) h.handleRequest(csr, createContext("Rename"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
//    */
}

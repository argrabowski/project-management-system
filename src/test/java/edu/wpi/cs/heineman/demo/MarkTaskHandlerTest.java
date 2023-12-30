package edu.wpi.cs.heineman.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.http.DecomposeTaskRequest;
import edu.wpi.cs.heineman.demo.http.DecomposeTaskResponse;
import edu.wpi.cs.heineman.demo.http.MarkTaskRequest;
import edu.wpi.cs.heineman.demo.http.MarkTaskResponse;
import edu.wpi.cs.heineman.demo.model.Task;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
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
//    	String id = "2bd2e263-1285-4e55-be91-ba03523d0566";
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
			// TODO Auto-generated catch block
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
		
    	MarkTaskRequest csr = new MarkTaskRequest(var);
    	MarkTaskHandler h = new MarkTaskHandler();
        
    	MarkTaskResponse resp = (MarkTaskResponse) h.handleRequest(csr, createContext("mark"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
//  */
}

package edu.wpi.cs.heineman.demo;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.http.listAllProjectsResponse;
import edu.wpi.cs.heineman.demo.model.Project;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListAllProjectsHandlerTesk extends LambdaTest {
    
    // overwrites into it
    @Test
    public void testListAllProjects() {
    	// create constant
    	ListAllProjectsHandler h = new ListAllProjectsHandler();
        
    	Object obj = new Object();
    	listAllProjectsResponse resp = (listAllProjectsResponse) h.handleRequest(obj, createContext("create"));
    	//System.out.println(csr.id);
 
        Assert.assertEquals(200, resp.statusCode);
    }
}

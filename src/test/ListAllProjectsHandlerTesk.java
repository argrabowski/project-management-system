import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import db.ProjectsDAO;
import http.listAllProjectsResponse;
import model.Project;

public class ListAllProjectsHandlerTesk extends LambdaTest {
    @Test
    public void testListAllProjects() {
    	ListAllProjectsHandler h = new ListAllProjectsHandler();

    	Object obj = new Object();
    	listAllProjectsResponse resp = (listAllProjectsResponse) h.handleRequest(obj, createContext("create"));

        Assert.assertEquals(200, resp.statusCode);
    }
}

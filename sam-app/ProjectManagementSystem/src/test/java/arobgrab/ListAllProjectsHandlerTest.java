package arobgrab;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import arobgrab.db.ProjectsDAO;
import arobgrab.http.ListAllProjectsResponse;
import arobgrab.model.Project;

public class ListAllProjectsHandlerTest extends LambdaTest {
    @Test
    public void testListAllProjects() {
    	ListAllProjectsHandler h = new ListAllProjectsHandler();

    	Object obj = new Object();
    	ListAllProjectsResponse resp = (ListAllProjectsResponse) h.handleRequest(obj, createContext("create"));

        Assert.assertEquals(200, resp.statusCode);
    }
}

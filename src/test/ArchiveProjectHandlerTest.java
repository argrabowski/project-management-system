import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import db.ProjectsDAO;
import http.AddTeammateRequest;
import http.ArchiveProjectRequest;
import http.ArchiveProjectResponse;
import http.CreateProjectRequest;
import http.CreateProjectResponse;
import model.Project;

public class ArchiveProjectHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming,  ArchiveProjectRequest.class);

        ArchiveProjectResponse resp = (ArchiveProjectResponse) handler.handleRequest(req, createContext("archive"));
        Assert.assertEquals(200, resp.statusCode);
    }

    void testFailInput(String incoming) throws IOException {
    	ArchiveProjectHandler handler = new ArchiveProjectHandler();
    	ArchiveProjectRequest req = new Gson().fromJson(incoming, ArchiveProjectRequest.class);

    	ArchiveProjectResponse resp = (ArchiveProjectResponse) handler.handleRequest(req, createContext("archive"));
        Assert.assertEquals(422, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);

        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

        ArchiveProjectRequest dcr = new ArchiveProjectRequest("x" + rnd);
        ArchiveProjectResponse d_resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(dcr, createContext("archive"));
        Assert.assertEquals("x" + rnd, d_resp.project.name);

        ArchiveProjectRequest ccr2 = new ArchiveProjectRequest("x" + rnd);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr2);  

        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }

        ArchiveProjectRequest dcrBad = new ArchiveProjectRequest("x" + rnd + rnd);
        d_resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(dcrBad, createContext("archive"));
        Assert.assertEquals(422, d_resp.statusCode);
    }

    @Test
    public void testShouldFail() {
        int rnd = (int) (Math.random() * 1000000);
        ArchiveProjectRequest ccr = new ArchiveProjectRequest("x" + rnd);

        ProjectsDAO projDao = new ProjectsDAO();
        Project project = null;
		try {
			project = projDao.getProject("x" + rnd);
		} catch (Exception e) {
			e.printStackTrace();
		}

        ArchiveProjectResponse resp = (ArchiveProjectResponse) new ArchiveProjectHandler().handleRequest(ccr, createContext("archive"));
        Assert.assertNull(project);

        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  

        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testArchiveSystemProject() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);
        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

    	ArchiveProjectRequest csr = new ArchiveProjectRequest("x" + rnd);
    	ArchiveProjectHandler h = new ArchiveProjectHandler();

    	ArchiveProjectResponse Aresp = (ArchiveProjectResponse) h.handleRequest(csr, createContext("archive"));

        Assert.assertEquals(200, Aresp.statusCode);
    }
}

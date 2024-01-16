import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import db.ProjectsDAO;
import db.TeammatesDAO;
import http.AddTeammateRequest;
import http.AddTeammateResponse;
import http.CreateProjectRequest;
import model.Project;
import model.Teammate;

public class AddTeammateHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	AddTeammateHandler handler = new AddTeammateHandler();
    	AddTeammateRequest req = new Gson().fromJson(incoming,  AddTeammateRequest.class);

        AddTeammateResponse resp = (AddTeammateResponse) handler.handleRequest(req, createContext("add"));
        Assert.assertEquals(200, resp.statusCode);
    }

    void testFailInput(String incoming) throws IOException {
    	AddTeammateHandler handler = new AddTeammateHandler();
    	AddTeammateRequest req = new Gson().fromJson(incoming, AddTeammateRequest.class);

    	AddTeammateResponse resp = (AddTeammateResponse) handler.handleRequest(req, createContext("add"));
        Assert.assertEquals(422, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(19900*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;

    	AddTeammateRequest ccr = new AddTeammateRequest(var, "coverage");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  

        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testShouldFail() {
    	ProjectsDAO dao = new ProjectsDAO();
    	int rndNum = (int)(990*(Math.random()));
    	Project project = new Project(rndNum + rndNum + "", false, 0);
    	project.id = "";

    	try {
			dao.createProject(project);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

    	TeammatesDAO mateDao = new TeammatesDAO();
    	int rndNum2 = (int)(990*(Math.random()));
    	Teammate teammate = new Teammate(rndNum2 + "", project.name);

    	try {
			mateDao.addTeammate(teammate);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

    	AddTeammateRequest ccr = new AddTeammateRequest(rndNum2 + "", project.name);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  

        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testCreateSystemProject() {
    	int rndNum = (int)(990*(Math.random()));
    	AddTeammateRequest csr = new AddTeammateRequest("to-delete-again"+rndNum, "coverage");
    	AddTeammateHandler h = new AddTeammateHandler();

    	AddTeammateResponse resp = (AddTeammateResponse) h.handleRequest(csr, createContext("add"));

        Assert.assertEquals(200, resp.statusCode);
    }
}

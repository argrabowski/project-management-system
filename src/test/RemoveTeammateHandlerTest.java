import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import db.TeammatesDAO;
import http.AddTeammateRequest;
import http.AddTeammateResponse;
import http.CreateProjectRequest;
import http.CreateProjectResponse;
import http.DeleteProjectResponse;
import http.RemoveTeammateRequest;
import http.RemoveTeammateResponse;
import model.Teammate;

public class RemoveTeammateHandlerTest extends LambdaTest {
    void testSuccessInput(String incoming) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();
    	RemoveTeammateRequest req = new Gson().fromJson(incoming,  RemoveTeammateRequest.class);

        RemoveTeammateResponse resp = (RemoveTeammateResponse) handler.handleRequest(req, createContext("remove"));
        Assert.assertEquals(200, resp.statusCode);
    }

    void testFailInput(String incoming) throws IOException {
    	RemoveTeammateHandler handler = new RemoveTeammateHandler();
    	RemoveTeammateRequest req = new Gson().fromJson(incoming, RemoveTeammateRequest.class);

    	RemoveTeammateResponse resp = (RemoveTeammateResponse) handler.handleRequest(req, createContext("remove"));
        Assert.assertEquals(422, resp.statusCode);
    }

    @Test
    public void testShouldBeOk() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;

    	AddTeammateRequest ccr = new AddTeammateRequest(var, "coverage");
    	AddTeammateResponse resp = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr, createContext("add"));

    	TeammatesDAO mateDao = new TeammatesDAO();
    	String name = "";
		try {
			name = mateDao.getTeammate(var, resp.project.name).name;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertEquals(var, name);

    	RemoveTeammateRequest rcr = new RemoveTeammateRequest("coverage", var);
    	RemoveTeammateResponse r_resp = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));

    	Teammate teammate = null;
    	try {
			teammate = mateDao.getTeammate(var, r_resp.project.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertNull(teammate);

    	String var2 = "throwAwayCoverage" + rndNum;

    	AddTeammateRequest ccr2 = new AddTeammateRequest(var2, "coverage");
    	AddTeammateResponse resp2 = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr2, createContext("add"));

        String SAMPLE_INPUT_STRING = new Gson().toJson(rcr);  

        try {
        	testSuccessInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }

    @Test
    public void testShouldFail() {
    	int rndNum = (int)(990*(Math.random()));
    	String var = "throwAwayCoverage" + rndNum;

    	AddTeammateRequest ccr = new AddTeammateRequest(var, "coverage");
    	AddTeammateResponse resp = (AddTeammateResponse) new AddTeammateHandler().handleRequest(ccr, createContext("add"));

    	TeammatesDAO mateDao = new TeammatesDAO();
    	String name = "";
		try {
			name = mateDao.getTeammate(var, resp.project.name).name;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertEquals(var, name);

    	RemoveTeammateRequest rcr = new RemoveTeammateRequest("coverage", var);
    	RemoveTeammateResponse r_resp = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));

    	Teammate teammate = null;
    	try {
			teammate = mateDao.getTeammate(var, r_resp.project.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Assert.assertNull(teammate);

    	RemoveTeammateRequest rcr2 = new RemoveTeammateRequest("coverage", var);
    	RemoveTeammateResponse r_resp2 = (RemoveTeammateResponse) new RemoveTeammateHandler().handleRequest(rcr, createContext("remove"));

    	String SAMPLE_INPUT_STRING = new Gson().toJson(rcr2);
        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
    }
}

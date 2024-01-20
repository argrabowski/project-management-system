package arobgrab;

import org.junit.Assert;
import org.junit.Test;

import arobgrab.http.CreateProjectRequest;
import arobgrab.http.CreateProjectResponse;
import arobgrab.http.DeleteProjectRequest;
import arobgrab.http.DeleteProjectResponse;

public class DeleteProjectHandlerTest extends LambdaTest {
    @Test
    public void testCreateAndDeleteproject() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);

        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);

        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }

    @Test
    public void testCreateAndDeleteprojectWithTeammte() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);

        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);

        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }

    @Test
    public void testCreateAndDeleteprojectWithMultTeammates() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);

        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);

        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }

    @Test
    public void testCreateAndDeleteprojectWithMultTeammatesTasks() {
        int rnd = (int) (Math.random() * 1000000);
        CreateProjectRequest ccr = new CreateProjectRequest("x" + rnd);

        CreateProjectResponse resp = (CreateProjectResponse) new CreateProjectHandler().handleRequest(ccr, createContext("create"));
        Assert.assertEquals("x" + rnd, resp.response);

        DeleteProjectRequest dcr = new DeleteProjectRequest("x" + rnd);
        DeleteProjectResponse d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals("x" + rnd, d_resp.name);

        d_resp = (DeleteProjectResponse) new DeleteProjectHandler().handleRequest(dcr, createContext("delete"));
        Assert.assertEquals(422, d_resp.statusCode);
    }
}

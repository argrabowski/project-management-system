import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import db.TeammatesDAO;
import model.Teammate;

public class TeammateDAOTest {
	@Test
	public void test() {
		try {
			UUID uid = UUID.randomUUID();
			String name = uid.toString().substring(0, 15);

			UUID uid2 = UUID.randomUUID();
			String name2 = uid2.toString().substring(0, 15);

			TeammatesDAO dao = new TeammatesDAO();
			Teammate t = new Teammate(name, "coverage");
			assertTrue(dao.addTeammate(t));

			Teammate t2 = dao.getTeammate(t.name, t.projectName);
			assertTrue(t2 != null);

			Teammate t3 = new Teammate(name2, "coverage");
			assertTrue(dao.addTeammate(t3));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error:" + e.getMessage());
		}
	}
}

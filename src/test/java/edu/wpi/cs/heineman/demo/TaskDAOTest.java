package edu.wpi.cs.heineman.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import edu.wpi.cs.heineman.demo.db.ProjectsDAO;
import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.model.Task;


public class TaskDAOTest {

	@Test
	public void test() {
		try {
			
			UUID uid = UUID.randomUUID();
			String id = uid.toString().substring(0, 15);
			
			UUID uidName = UUID.randomUUID();
			String name = uidName.toString().substring(0, 15);
			
			UUID uid2 = UUID.randomUUID();
			String id2 = uid2.toString().substring(0, 15);
			
			UUID uidName2 = UUID.randomUUID();
			String name2 = uidName2.toString().substring(0, 15);
			
			TasksDAO dao = new TasksDAO();
			String outlineid = "0";
			int firstDigit = 0;
			ArrayList<Task> tasks = new ArrayList<Task>();
			ProjectsDAO projDao = new ProjectsDAO();
			// clear the project
//			try {
//				projDao.deleteProject(projDao.getProject("coverage"));
//			} catch (Exception e) {
//			}
//			new CreateProjectHandler().createProject("coverage");
			ArrayList<Task> allTasks = dao.getAllTasksByProject("coverage");
			for (Task task : allTasks) {
				tasks.add(task);
				firstDigit = Integer.parseInt(task.outlineIdentifier.substring(0, 1));
				if (tasks.size() < 7) {
					outlineid = String.valueOf(firstDigit + 1);
				}
				else {
					// To make testing simpler, this simply resets the project in the ProjectsDAO
					// and the tasks ArrayList<Task>, whenever the size of tasks exceeds 7.
					tasks.clear();
					try {
						projDao.deleteProject(projDao.getProject("coverage"));
					} catch (Exception e) {
					}
					new CreateProjectHandler().createProject("coverage");
				}
			}
//			for (Task deleting : allTasks) {
//				dao.removeTask(deleting);
//			}
			String outlineStr = String.valueOf(outlineid);
			Task t = new Task(name, "coverage", outlineStr, id, false, false);
			assertTrue(dao.addTask(t));
			Task t2 = dao.getTask(t.id);
			assertTrue(t2 != null);
			
			assertTrue(dao.removeTask(t2));
			Task t3 = dao.getTask(t2.id);
			assertTrue(t3 == null);
			
			Task t5 =  new Task(name, "coverage", outlineStr, id2, false, false);
			assertTrue(dao.addTask(t5));
			dao.updateTask(t2, t5.id);
			assertTrue(t2 != t5);
			assertTrue(t2.id == t5.id);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error:" + e.getMessage());
		}
	}

}

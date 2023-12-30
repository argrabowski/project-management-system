package edu.wpi.cs.heineman.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import edu.wpi.cs.heineman.demo.db.TasksDAO;
import edu.wpi.cs.heineman.demo.db.TeammatesDAO;
import edu.wpi.cs.heineman.demo.db.AssignmentsDAO;
import edu.wpi.cs.heineman.demo.model.Assignment;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

public class AssignmentDAOTest {

	@Test
	public void test() {
		try {
			UUID mateUUID = UUID.randomUUID();
			String mateName = mateUUID.toString().substring(0, 15);

			TeammatesDAO teamDao = new TeammatesDAO();
			Teammate mate = new Teammate(mateName, "coverage");
			assertTrue(teamDao.addTeammate(mate));
			
			UUID taskNameUUID = UUID.randomUUID();
			String taskName = taskNameUUID.toString().substring(0, 15);
			
			UUID taskIdUUID = UUID.randomUUID();
			String taskId = taskIdUUID.toString();

			TasksDAO taskDao = new TasksDAO();
			Task task = new Task(taskName, "coverage", "2.0.2", taskId, false, false);
			assertTrue(taskDao.addTask(task));
			
			AssignmentsDAO assignDao = new AssignmentsDAO();
			ArrayList<Assignment> assignments = assignDao.getAllAssignmentsByProject("coverage");
			int assignmentsSize = assignments.size();
			assertTrue(assignDao.addAssignment("coverage", task.id, mate.name));
			Assignment assignment = assignDao.getAssignment("coverage", task.id, mate.name);
			assignments = assignDao.getAllAssignmentsByProject("coverage");
			
			assertTrue(assignments.size() == assignmentsSize + 1);
			assertTrue(assignment.teammate.name.equals(mate.name));
			assertTrue(assignment.teammate.projectName.equals(mate.projectName));
			
			assertTrue(assignment.task.name.equals(task.name));
			assertTrue(assignment.task.projectName.equals(task.projectName));
			assertTrue(assignment.task.outlineIdentifier.equals(task.outlineIdentifier));
			assertTrue(assignment.task.id.equals(task.id));
			assertTrue(assignment.task.isMarked == task.isMarked);
			assertTrue(assignment.task.hasSubtasks == task.hasSubtasks);
			
			assertTrue(assignDao.removeAssignment("coverage", task.id, mate.name));
			assertTrue(assignDao.getAssignment("coverage", task.id, mate.name) == null);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Error:" + e.getMessage());
		}
	}
}

package edu.wpi.cs.heineman.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.heineman.demo.model.Assignment;
import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

public class AssignmentsDAO {
	java.sql.Connection conn;
	final String tblName = "assignments";

	public AssignmentsDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	
	public Assignment getAssignment(String pname, String taskid, String teammatename) throws Exception {

		try {
			Assignment assignment = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE pname=? AND taskid=? AND teammatename=?;");
			ps.setNString(1, pname);
			ps.setNString(2, taskid);
			ps.setNString(3, teammatename);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				assignment = generateAssignment(resultSet);
			}
			resultSet.close();
			ps.close();

			return assignment;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting assignment: " + e.getMessage());
		}
	}

//	public boolean updateTask(Task task) throws Exception {
//		try {
//			String query = "UPDATE " + tblName + " SET value=? WHERE taskid=?;";
//			PreparedStatement ps = conn.prepareStatement(query);
//			ps.setString(1, task.id);
//			int numAffected = ps.executeUpdate();
//			ps.close();
//
//			return (numAffected == 1);
//		} catch (Exception e) {
//			throw new Exception("Failed to update task: " + e.getMessage());
//		}
//	}

	public boolean addAssignment(String pname, String taskid, String teammatename) throws Exception {
		try {
//			List<Task> allTasks = getAllTasksByProject(task.projectName);
//			for (Task taskSpef : allTasks) {
//				if (taskSpef.name.equals(task.name)) {
//					return false;
//				}
//			}
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE pname=? AND taskid=? AND teammatename=?;");
			ps.setString(1, pname);
			ps.setString(2, taskid);
			ps.setString(3, teammatename);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				generateAssignment(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName + " (pname, taskid, teammatename) values(?,?,?);");
			ps.setString(1, pname);
			ps.setString(2, taskid);
			ps.setString(3, teammatename);
			ps.execute();
			
			return true;
		} catch (Exception e) {
			throw new Exception("Failed to add assignment: " + e.getMessage());
		}
	}

	/*
	public List<Task> getAllTasksByParent(String projectName, String parentid) throws Exception {

		List<Task> allTasks = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Task c = generateTask(resultSet);
				if (c.projectName.equals(projectName) && c.parentTaskID.equals(parentid)) {
					allTasks.add(c);
				}
			}
			resultSet.close();
			statement.close();
			return allTasks;

		} catch (Exception e) {
			throw new Exception("Failed in getting all tasks: " + e.getMessage());
		}
	}
	*/

	public ArrayList<Assignment> getAllTasksByTeammate(String teammatename) throws Exception {

		ArrayList<Assignment> assignmentsList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teammatename=?;");
			ps.setString(1, teammatename);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Assignment c = generateAssignment(resultSet);
				assignmentsList.add(c);
			}
			
			resultSet.close();
			statement.close();
			return assignmentsList;

		} catch (Exception e) {
			throw new Exception("Failed in getting task assignments: " + e.getMessage());
		}
	}
	
	public ArrayList<Assignment> getAllTeammatesByTask(String taskid) throws Exception {

		ArrayList<Assignment> assignmentsList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE taskid=?;");
			ps.setString(1, taskid);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Assignment c = generateAssignment(resultSet);
				assignmentsList.add(c);
			}
			
			resultSet.close();
			statement.close();
			return assignmentsList;

		} catch (Exception e) {
			throw new Exception("Failed in getting teammate assignments: " + e.getMessage());
		}
	}
	
	public ArrayList<Assignment> getAllAssignmentsByProject(String projectName) throws Exception {

		ArrayList<Assignment> assignmentsList = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE pname=?;");
			ps.setString(1, projectName);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Assignment c = generateAssignment(resultSet);
				assignmentsList.add(c);
			}
			
			resultSet.close();
			statement.close();
			return assignmentsList;

		} catch (Exception e) {
			throw new Exception("Failed in getting teammate assignments: " + e.getMessage());
		}
	}

	private Assignment generateAssignment(ResultSet resultSet) throws Exception {
		TasksDAO tasksDAO = new TasksDAO();
		TeammatesDAO teammatesDAO = new TeammatesDAO();
		
		String projectName = resultSet.getString("pname");
		Task task = tasksDAO.getTask(resultSet.getString("taskid"));
		Teammate teammate = teammatesDAO.getTeammate(resultSet.getString("teammatename"), projectName);
		
		return new Assignment(projectName, task, teammate);
	}

	public boolean removeAssignment(String pname, String taskid, String teammatename) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE pname=? AND taskid=? AND teammatename=?;");
			ps.setNString(1, pname);
			ps.setNString(2, taskid);
			ps.setNString(3, teammatename);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to remove assignment: " + e.getMessage());
		}
	}
}
package edu.wpi.cs.heineman.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.heineman.demo.model.Task;
import edu.wpi.cs.heineman.demo.model.Teammate;

public class TasksDAO {
	java.sql.Connection conn;
	final String tblName = "tasks";

	public TasksDAO() {
		try  {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public Task getTask(String id) throws Exception {

		try {
			Task task = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE taskid=?;");
			ps.setNString(1, id);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				task = generateTask(resultSet);
			}
			resultSet.close();
			ps.close();

			return task;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting task: " + e.getMessage());
		}
	}

	public boolean updateTask(Task task, String newID) throws Exception {
		try {
			String query = "UPDATE " + tblName + " SET taskid=? WHERE taskid=?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, newID);
			ps.setString(2, task.id);
			int numAffected = ps.executeUpdate();
			ps.close();

			task.id = newID;
			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to update task: " + e.getMessage());
		}
	}
	
	public boolean markTask(Task task) throws Exception {
		try {
			int boolInt = 0;
			if (task.isMarked) {
				task.isMarked = false;
			}
			else {
				boolInt = 1;
				task.isMarked = true;
			}
			PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET marked=? WHERE taskid=?;");
			ps.setInt(1, boolInt);
			ps.setNString(2, task.id);
			ps.execute();
            ps.close();
            
            return true;
		} catch (Exception e) {
			throw new Exception("Failed to mark task: " + e.getMessage());
		}
	}
	
	public boolean renameTask(Task task, String newName) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET taskname=? WHERE taskid=?;");
			ps.setNString(1, newName);
			ps.setNString(2, task.id);
			ps.execute();
            ps.close();
            
            return true;
		} catch (Exception e) {
			throw new Exception("Failed to mark task: " + e.getMessage());
		}
	}

	public boolean addTask(Task task) throws Exception {
		try {
//			List<Task> allTasks = getAllTasksByProject(task.projectName);
//			for (Task taskSpef : allTasks) {
//				if (taskSpef.name.equals(task.name)) {
//					return false;
//				}
//			}
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE taskid=?;");
			ps.setString(1, task.id);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				generateTask(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName + " (taskname, taskid, outlineid, marked, teammatename, pname, hassubtasks) values(?,?,?,?,?,?,?);");
			ps.setString(1, task.name);
			ps.setString(2, task.id);
			ps.setString(3, task.outlineIdentifier);
			ps.setBoolean(4, false);
			ps.setString(5, "");
			ps.setString(6, task.projectName);
			ps.setBoolean(7,  false);
			ps.execute();
			
			return true;
		} catch (Exception e) {
			throw new Exception("Failed to insert task: " + e.getMessage());
		}
	}
	
	public boolean subtaskifyTask(Task task) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE " + tblName + " SET hassubtasks=? WHERE taskid=?;");
			ps.setInt(1, 1);
			ps.setNString(2, task.id);
			ps.execute();
            ps.close();
            
            return true;
		} catch (Exception e) {
			throw new Exception("Failed to mark task: " + e.getMessage());
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

	public ArrayList<Task> getAllTasksByProject(String projectName) throws Exception {

		ArrayList<Task> allTasks = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Task c = generateTask(resultSet);
				if (c.projectName.equals(projectName)) {
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

	private Task generateTask(ResultSet resultSet) throws Exception {
		String taskName  = resultSet.getString("taskname");
		String projectName = resultSet.getString("pname");
		String outlineIdentifier = resultSet.getString("outlineid");
		String taskid = resultSet.getString("taskid");
		int marked = resultSet.getInt("marked");
		boolean isMarked = (marked == 1);
		int subtasks = resultSet.getInt("hassubtasks");
		boolean hasSubtasks = (subtasks == 1);
		return new Task (taskName, projectName, outlineIdentifier, taskid, isMarked, hasSubtasks);
	}

	public boolean removeTask(Task task) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE taskid = ?;");
			ps.setString(1, task.id);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to remove task: " + e.getMessage());
		}
	}
}
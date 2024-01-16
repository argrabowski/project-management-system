import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Project;
import model.Task;
import model.Teammate;

public class ProjectsDAO { 
	java.sql.Connection conn;

	final String tblName = "projects";

    public ProjectsDAO() {
    	try {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Project getProject(String name) throws Exception {
        try {
            Project project = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE pname=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                project = generateProject(resultSet);
            }
            resultSet.close();
            ps.close();

            return project;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting project: " + e.getMessage());
        }
    }

    public Project updateProject(Project project) throws Exception {
        try {
        	TasksDAO taskDao = new TasksDAO();
        	List<Task> tasks = taskDao.getAllTasksByProject(project.name);

        	int subtaskNum = 0;
        	int subTasksMarked = 0;

        	for (Task task : tasks) {
        		if (!(task.hasSubtasks)) {
        			subtaskNum++;
        		}
        		if (task.isMarked && !(task.hasSubtasks)) {
        			subTasksMarked++;
        		}
        	}

        	float completionPercent = (float) subTasksMarked / subtaskNum;
        	project.completionStatus = (int) (completionPercent * 100);

        	String query = "UPDATE " + tblName + " SET completionstatus=? WHERE pname=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, project.completionStatus);
            ps.setString(2, project.name);
            int numAffected = ps.executeUpdate();
            ps.close();

            return project;
        } catch (Exception e) {
            throw new Exception("Failed to update project: " + e.getMessage());
        }
    }

    public boolean archiveProject(Project project) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET archived=? WHERE pname=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setBoolean(1, project.isArchived);
            ps.setString(2, project.name);
            int numAffected = ps.executeUpdate();
            ps.close();

            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to archive project: " + e.getMessage());
        }
    }

    public boolean deleteProject(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE pname = ?;");
            ps.setString(1, project.name);
            int numAffected = ps.executeUpdate();
            ps.close();

            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to delete project: " + e.getMessage());
        }
    }

    public boolean createProject(Project project) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE pname = ?;");
            ps.setString(1, project.name);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Project c = generateProject(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (pname,pid,archived,completionstatus) values(?,?,?,?);");
            ps.setString(1, project.name);
            ps.setString(2, project.id);
            ps.setBoolean(3, false);
            ps.setInt(4,0);
            ps.execute();

            return true;
        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }

    public List<Project> getAllProjects() throws Exception {
        List<Project> allProjects = new ArrayList<>();

        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Project c = generateProject(resultSet);
                allProjects.add(c);
            }

            resultSet.close();
            statement.close();

            return allProjects;
        } catch (Exception e) {
            throw new Exception("Failed in getting constants: " + e.getMessage());
        }
    }

    private Project generateProject(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("pname");
        boolean isArchived = resultSet.getBoolean("archived");
        int completionStatus = resultSet.getInt("completionstatus");
        boolean isArchived = resultSet.getBoolean("isArchived");
        float completionStatus = resultSet.getFloat("completionStatus");

        return new Project(name, isArchived, completionStatus);
    }
}

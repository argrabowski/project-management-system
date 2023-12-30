package edu.wpi.cs.heineman.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.heineman.demo.model.Teammate;

/**
 * Note that CAPITALIZATION matters regarding the table name. If you create with 
 * a capital "Constants" then it must be "Constants" in the SQL queries.
 */
public class TeammatesDAO { 

	java.sql.Connection conn;
	
	final String tblName = "teammates";   // Exact capitalization

    public TeammatesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public Teammate getTeammate(String name, String projectName) throws Exception {
        
        try {
            Teammate teammate = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teammatename=? AND pname=?;");
            ps.setString(1,  name);
            ps.setString(2, projectName);
            ResultSet resultSet = ps.executeQuery();
            
        	while (resultSet.next()) {
        		System.out.println("HERE");
                teammate = generateTeammate(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return teammate;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting teammate: " + e.getMessage());
        }
    }
    
    public boolean updateTeammate(Teammate teammate) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET teammatename=? WHERE pname=? AND teammatename=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setNString(1, teammate.name);
            ps.setString(2, teammate.projectName);
            ps.setString(3, teammate.name);
            
            System.out.println(ps.toString());
            
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }

    public boolean addTeammate(Teammate teammate) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teammatename=? AND pname = ?;");
            ps.setString(1, teammate.name);
            ps.setString(2, teammate.projectName);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Teammate c = generateTeammate(resultSet);
                resultSet.close();
                return false;
            }
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (teammatename, taskid, pname) values(?,?,?);");
            ps.setString(1, teammate.name);
            ps.setString(2, "");
            ps.setString(3, teammate.projectName);
            ps.execute();
            ps.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert teammate: " + e.getMessage());
        }
    }


    public List<Teammate> getAllTeammates(String projectName) throws Exception {
        
        List<Teammate> allTeammates = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Teammate c = generateTeammate(resultSet);
                if (c.projectName.equals(projectName)) {
                    allTeammates.add(c);
                }
            }
            resultSet.close();
            statement.close();
            return allTeammates;

        } catch (Exception e) {
            throw new Exception("Failed in getting teammates: " + e.getMessage());
        }
    }
    
    private Teammate generateTeammate(ResultSet resultSet) throws Exception {
        String name  = resultSet.getString("teammatename");
        String projectName = resultSet.getString("pname");
        return new Teammate (name, projectName);
    }

	public boolean removeTeammate(Teammate teammate) throws Exception {
		try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE pname = ? AND teammatename = ?;");
            ps.setString(1, teammate.projectName);
            ps.setString(2, teammate.name);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to remove teammate: " + e.getMessage());
        }
	}

}

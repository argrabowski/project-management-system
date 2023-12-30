package edu.wpi.cs.heineman.demo.http;

import java.util.ArrayList;
import java.util.UUID;

public class CreateProjectRequest {
	public String name;
	public String id;
	public boolean system;
	
//	public ArrayList<String> taskNames;
//	public ArrayList<String> teammateNames;
	
	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getID( ) { return id; }
	public void setID(String id) { this.id = id; }
	
	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }
	
//	public ArrayList<String> getTaskNames() { return taskNames; }
//	public void setTaskNames(ArrayList<String> taskNames) { this.taskNames = taskNames; }
//	
//	public ArrayList<String> getTeammateNames() { return teammateNames; }
//	public void setTeammateNames(ArrayList<String> teammateNames) { this.teammateNames = teammateNames; }
	
	public CreateProjectRequest() {
	}
	//, ArrayList<String> askNames, ArrayList<String> mateNames
	public CreateProjectRequest (String n) {
		this.name = n;
		
		//this.id = id;
//		this.taskNames = askNames;
//		this.teammateNames = mateNames;
	}
	
	public String toString() {
		//return "CreateProject(" + name + "," + id + ")";
		return "CreateProject(" + name + ")";
	}
}
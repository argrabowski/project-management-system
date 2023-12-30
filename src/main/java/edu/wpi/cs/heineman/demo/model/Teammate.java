package edu.wpi.cs.heineman.demo.model;

import java.util.List;

public class Teammate {

	public String name;
	public String projectName;
	public List<String> taskIDs;
	
	public Teammate(String name, String projectName) {
		this.name = name;
		this.projectName = projectName;
	}
	
	Teammate(String name, String projectName, List<String> taskIDs) {
		this.name = name;
		this.projectName = projectName;
		this.taskIDs = taskIDs;
	}
	
}

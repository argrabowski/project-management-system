package edu.wpi.cs.heineman.demo.model;

import java.util.ArrayList;

public class Task {
	public String name;
	public String projectName;
	public String outlineIdentifier;
	public String id;
	public ArrayList<String> teammateIDs;
	public boolean isMarked;
	public boolean hasSubtasks;

	public Task(String name, String projectName, String outlineIdentifier, String id, boolean isMarked, boolean hasSubtasks) {
		this.name = name;
		this.projectName = projectName;
		this.outlineIdentifier = outlineIdentifier;
		this.id = id;
		this.isMarked = isMarked;
		this.hasSubtasks = hasSubtasks;
	}
}
package arobgrab.model;

import java.util.ArrayList;

public class Assignment {
	public String projectName;
	public Task task;
	public Teammate teammate;

	public Assignment(String projectName, Task task, Teammate teammate) {
		this.projectName = projectName;
		this.task = task;
		this.teammate = teammate;
	}
}

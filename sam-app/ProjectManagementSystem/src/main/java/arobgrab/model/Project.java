package arobgrab.model;

import java.util.List;
import java.util.ArrayList;

public class Project {
	public String name;
	public String id;
	public final boolean system;

	public boolean isArchived;
	public int completionStatus;

	public List<Teammate> teammates;
	public List<Task> tasks;
	public List<Assignment> assignments;

	public Project(String name, String id) {
		this.name = name;
		this.id = id;
		this.system = false;
	}

	public Project(String name, String id, int completionStatus) {
		this.name = name;
		this.id = id;
		this.completionStatus = completionStatus;
		this.system = false;
	}

	public Project(String name, boolean archived, int completionStatus) {
		this.name = name;
		this.isArchived = archived;
		this.completionStatus = completionStatus;
		this.system = false;
	}

	public Project(String name, String id, boolean system) {
		this.name = name;
		this.id = id;
		this.system = system;
	}

	public Project(String name, boolean archived) {
		this.name = name;
		this.isArchived = archived;
		this.system = false;
	}

	public boolean getSystem() { return system; }

	public boolean equals (Object o) {
		if (o == null) { return false; }

		if (o instanceof Project) {
			Project other = (Project) o;
			return id.equals(other.id);
		}

		return false;
	}

	public String toString() {
		String sysString = "";
		if (system) { sysString = " (system)"; }
		return "[" + name + " = " + id + sysString + "]";
	}
}

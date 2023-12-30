package edu.wpi.cs.heineman.demo.http;

public class DecomposeTaskRequest {
	public String id;
	public String subtaskNames;

	public DecomposeTaskRequest() {}

	public DecomposeTaskRequest (String id, String subtaskNames) {
		this.id = id;
		this.subtaskNames = subtaskNames;
	}

	public void setID(String id) {this.id = id; }
	public String getID() {return id; }

	public void setSubtaskNames(String subtaskNames) {this.subtaskNames = subtaskNames; }
	public String getSubtaskNames() {return subtaskNames; }

	public String toString() {
		return "DecomposeTaskRequest(" + this.toString() + ")";
	}
}
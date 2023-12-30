package edu.wpi.cs.heineman.demo.http;

public class RenameTaskRequest {
	public String id;
	public String newName;

	public RenameTaskRequest() {}

	public RenameTaskRequest (String id, String newName) {
		this.id = id;
		this.newName = newName;
	}
	
	public void setID(String id) {this.id = id; }
	public String getID() {return id; }
	
	public void setNewName(String newName) {this.newName = newName; }
	public String getNewName() {return newName; }

	public String toString() {
		return "MarkTaskRequest(" + this.id + ", " + newName + ")";
	}
}
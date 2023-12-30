package edu.wpi.cs.heineman.demo.http;

import edu.wpi.cs.heineman.demo.model.Project;

/**
 * In most cases the response is the name of the constant that was being created.
 * 
 * if an error of some sort, then the response describes that error.
 *  
 */
public class AddTeammateResponse {
	public final Project project;
	public final int statusCode;
	
	public AddTeammateResponse (Project project, int code) {
		this.project = project;
		this.statusCode = code;
	}
	
	// 200 means success
	public AddTeammateResponse (Project project) {
		this.project = project;
		this.statusCode = 200;
	}
	
	public String toString() {
		return "Response(" + project + ")";
	}
}

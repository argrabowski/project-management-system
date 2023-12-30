package edu.wpi.cs.heineman.demo.http;

import edu.wpi.cs.heineman.demo.model.Project;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class RemoveTeammateResponse {
	public final Project project;
	public final int statusCode;
	public final String error;
	
	public RemoveTeammateResponse (Project project, int statusCode) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public RemoveTeammateResponse (Project project, int statusCode, String errorMessage) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute? Nope, just the right amount of cute~
			return "RemoveResponse(" + project.name + ")";
		} else {
			return "ErrorResult(" + project.name + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}

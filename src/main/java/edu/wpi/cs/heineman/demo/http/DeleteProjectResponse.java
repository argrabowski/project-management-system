package edu.wpi.cs.heineman.demo.http;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class DeleteProjectResponse {
	public final String name;
	public final int statusCode;
	public final String error;
	
	public DeleteProjectResponse (String name, int statusCode) {
		this.name = name;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeleteProjectResponse (String name, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.name = name;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute? Nope, just the right amount of cute~
			return "DeleteResponse(" + name + ")";
		} else {
			return "ErrorResult(" + name + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}

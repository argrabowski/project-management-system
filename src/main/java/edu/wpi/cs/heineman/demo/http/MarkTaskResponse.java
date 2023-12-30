package edu.wpi.cs.heineman.demo.http;

import edu.wpi.cs.heineman.demo.model.Project;

public class MarkTaskResponse {
	public final Project project;
	public final int statusCode;
	public final String error;

	public MarkTaskResponse (Project project, int statusCode) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = "";
	}

	public MarkTaskResponse (Project project, int statusCode, String errorMessage) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}

	public String toString() {
		if (statusCode / 100 == 2) {
			return "MarkTaskResponse(" + project.tasks.toString() + ")";
		} else {
			return "ErrorResult(" + project.tasks.toString() + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
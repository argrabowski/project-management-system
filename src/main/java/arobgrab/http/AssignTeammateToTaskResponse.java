package arobgrab.http;

import arobgrab.model.Project;

public class AssignTeammateToTaskResponse {
	public final Project project;
	public final int statusCode;
	public final String error;

	public AssignTeammateToTaskResponse (Project project, int statusCode) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = "";
	}

	public AssignTeammateToTaskResponse (Project project, int statusCode, String errorMessage) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}

	public String toString() {
		if (statusCode / 100 == 2) {
			return "AddTaskResponse(" + project.tasks.toString() + ")";
		} else {
			return "ErrorResult(" + project.tasks.toString() + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}

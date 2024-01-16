import model.Project;

public class RemoveTeammateResponse {
	public final Project project;
	public final int statusCode;
	public final String error;

	public RemoveTeammateResponse (Project project, int statusCode) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = "";
	}

	public RemoveTeammateResponse (Project project, int statusCode, String errorMessage) {
		this.project = project;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}

	public String toString() {
		if (statusCode / 100 == 2) {
			return "RemoveResponse(" + project.name + ")";
		} else {
			return "ErrorResult(" + project.name + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}

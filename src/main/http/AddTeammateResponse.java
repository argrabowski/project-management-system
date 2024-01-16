import model.Project;

public class AddTeammateResponse {
	public final Project project;
	public final int statusCode;

	public AddTeammateResponse (Project project, int code) {
		this.project = project;
		this.statusCode = code;
	}

	public AddTeammateResponse (Project project) {
		this.project = project;
		this.statusCode = 200;
	}

	public String toString() {
		return "Response(" + project + ")";
	}
}

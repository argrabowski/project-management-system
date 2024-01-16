import java.util.ArrayList;
import java.util.List;

import model.Project;

public class listAllProjectsResponse {
	public final List<Project> list;
	public final int statusCode;
	public final String error;

	public listAllProjectsResponse (List<Project> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}

	public listAllProjectsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Project>();
		this.statusCode = code;
		this.error = errorMessage;
	}

	public String toString() {
		if (list == null) { return "EmptyProjects"; }
		return "AllProjects(" + list.size() + ")";
	}
}

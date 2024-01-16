public class RemoveTeammateRequest {
	public String projectName;
	public String name;

	public RemoveTeammateRequest (String pN, String n) {
		this.projectName = pN;
		this.name = n;
	}

	public void setName(String name) {this.name = name; }
	public String getName() {return name; }

	public void setProjectName(String projectName) {this.projectName = projectName; }
	public String getProjectName() {return projectName; }

	public RemoveTeammateRequest() {}

	public String toString() {
		return "Remove(" + name + ")";
	}
}

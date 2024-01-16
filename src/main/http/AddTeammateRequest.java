public class AddTeammateRequest {
	public String projectName;
	public String name;
	public boolean system;

	public String getProjectName( ) { return projectName; }
	public void setProject(String projectName) { this.projectName = projectName; }

	public String getTeammateName( ) { return name; }
	public void setTeammateName(String teammateName) { this.name = teammateName; }

	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }

	public AddTeammateRequest() {}

	public AddTeammateRequest (String n, String p) {
		this.name = n;
		this.projectName = p;
	}

	public String toString() {
		return "AddTeammate(" + name + ", " + projectName + ")";
	}
}

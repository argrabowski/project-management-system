public class AddTaskRequest {
	public String taskName;
	public String projectName;

	public AddTaskRequest() {}

	public AddTaskRequest (String taskName, String projectName) {
		this.taskName = taskName;
		this.projectName = projectName;
	}

	public void setName(String taskName) {this.taskName = taskName; }
	public String getName() {return taskName; }

	public void setProjectName(String projectName) {this.projectName = projectName; }
	public String getProjectName() {return projectName; }

	public String toString() {
		return "AddTaskRequest(" + this.taskName + ")";
	}
}

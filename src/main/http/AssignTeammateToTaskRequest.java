public class AssignTeammateToTaskRequest {
	public String teammateName;
	public String taskId;
	public String projectName;

	public AssignTeammateToTaskRequest() {}

	public AssignTeammateToTaskRequest (String teammateName, String taskId, String projectName) {
		this.teammateName = teammateName;
		this.taskId = taskId;
		this.projectName = projectName;
	}

	public void setTeammateName(String teammateName) {this.teammateName = teammateName; }
	public String getTeammateName() {return teammateName; }

	public void setTaskId(String taskId) {this.taskId = taskId; }
	public String getTaskOutlineId() {return taskId; }

	public void setProjectName(String projectName) {this.projectName = projectName; }
	public String getProjectName() {return projectName; }

	public String toString() {
		return "AddTaskRequest(" + this.teammateName + ", " + this.taskId + ", " + this.projectName + ")";
	}
}

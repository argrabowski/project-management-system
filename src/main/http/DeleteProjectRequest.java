public class DeleteProjectRequest {
	public String name;

	public void setName(String name) {this.name = name; }
	public String getName() {return name; }

	public DeleteProjectRequest (String n) {
		this.name = n;
	}

	public DeleteProjectRequest() {}

	public String toString() {
		return "Delete(" + name + ")";
	}
}

public class MarkTaskRequest {
	public String id;

	public MarkTaskRequest() {}

	public MarkTaskRequest (String id) {
		this.id = id;
	}

	public void setID(String id) {this.id = id; }
	public String getID() {return id; }

	public String toString() {
		return "MarkTaskRequest(" + this.id + ")";
	}
}

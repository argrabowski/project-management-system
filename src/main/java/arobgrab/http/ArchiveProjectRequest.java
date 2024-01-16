package arobgrab.http;

public class ArchiveProjectRequest {
	public String name;

	public ArchiveProjectRequest() {}

	public ArchiveProjectRequest (String name) {
		this.name = name;
	}

	public void setName(String name) {this.name = name; }
	public String getName() {return name; }

	public String toString() {
		return "ArchiveProjectRequest(" + this.name + ")";
	}
}

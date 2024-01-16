import java.util.ArrayList;
import java.util.UUID;

public class CreateProjectRequest {
	public String name;
	public String id;
	public boolean system;

	public String getName( ) { return name; }
	public void setName(String name) { this.name = name; }

	public String getID( ) { return id; }
	public void setID(String id) { this.id = id; }

	public boolean getSystem() { return system; }
	public void setSystem(boolean system) { this.system = system; }

	public CreateProjectRequest() {}

	public CreateProjectRequest (String n) {
		this.name = n;
	}

	public String toString() {
		return "CreateProject(" + name + ")";
	}
}

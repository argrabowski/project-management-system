package arobgrab.http;

public class AddRequest {
	String arg1;
	String arg2;

	public String getArg1() { return arg1; }
	public void setArg1(String arg1) { this.arg1 = arg1; }

	public String getArg2() { return arg2; }
	public void setArg2(String arg2) { this.arg2 = arg2; }

	public String toString() {
		return "Add(" + arg1 + "," + arg2 + ")";
	}

	public AddRequest (String arg1, String arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public AddRequest() {}
}

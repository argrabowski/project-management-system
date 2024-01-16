package arobgrab.http;

public class CreateProjectResponse {
	public final String response;
	public final int httpCode;

	public CreateProjectResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}

	public CreateProjectResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}

	public String toString() {
		return "Response(" + response + ")";
	}
}

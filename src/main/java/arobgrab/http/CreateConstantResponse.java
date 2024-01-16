package arobgrab.http;

public class CreateConstantResponse {
	public final String response;
	public final int httpCode;

	public CreateConstantResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}

	public CreateConstantResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}

	public String toString() {
		return "Response(" + response + ")";
	}
}

package arobgrab.http;

public class GenericErrorResponse {
	public int statusCode;
	public String error;

	public GenericErrorResponse(int code, String errorMessage) {
		this.statusCode = code;
		this.error = errorMessage;
	}

	public String toString() {
		return "GenericErrorResponse(" + error + ")";
	}
}

package arobgrab.http;

public class AddResponse {
	public String result;
	public int statusCode;
	public String error;

	public AddResponse (double value, int statusCode) {
		this.result = "" + value;
		this.statusCode = statusCode;
		this.error = "";
	}

	public AddResponse (int statusCode, String errorMessage) {
		this.result = "";
		this.statusCode = statusCode;
		this.error = errorMessage;
	}

	public String toString() {
		if (statusCode / 100 == 2) {
			return "Result(" + result + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}

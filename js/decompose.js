function processDecomposeResponse(result) {
	console.log("rename :" + result);
	location.reload();
}

function promptDecompose(id, e) {
	let namelist = window.prompt("List subtask names (use a comma to separate):");
	processDecompose(id, namelist);
}

function processDecompose(id, namelist) {
	var data = {};
	data["id"] = id;
	data["subtaskNames"]=namelist;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", decompose_url, true);
	xhr.send(js);
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				var res = xhr.responseText;
				var js = JSON.parse(res);
				console.log ("XHR:" + xhr.responseText);
				if (js["statusCode"]==200) {
					processDecomposeResponse(xhr.responseText);
				} else {
					window.alert("Task cannot be decomposed.");	
				}
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processDecomposeResponse("N/A");
		}
	};
	xhr.send(null);
}

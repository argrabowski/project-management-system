function refreshProjectsList(e) {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_url, true);
	xhr.send();
	console.log("sent");
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			processListResponse(xhr.responseText);
		} else {
			processListResponse("N/A");
		}
	};
}

function processListResponse(result) {
	console.log("res:" + result);
	var js = JSON.parse(result);
	var projList = document.getElementById('projectList');
	var output = "";
	for (var i = 0; i < js.list.length; i++) {
		var projectJson = js.list[i];
		console.log(projectJson);
		var pname = projectJson["name"];
		var arch = projectJson["isArchived"]
		var sysvar = projectJson["system"];
		var completion = projectJson["completionStatus"];
		if (sysvar) {
			output = output + "<div id=\"const" + pname + "\"><b>" + pname + "<br></div>";
		} else if(arch == true) {
			output = output + "<div id=\"const" + pname + "\"><b>" + pname + "<a href='javaScript:requestDelete(\"" + pname + "\")'><img src='../imgs/delete-icon.png'></img></a> <i>"+completion+"%</i><br></div>";
		} else {
			output = output + "<div id=\"const" + pname + "\"><b>" + pname + "<a href='javaScript:requestDelete(\"" + pname + "\")'><img src='../imgs/delete-icon.png'></img></a> <a href='javaScript:requestArchive(\"" + pname + "\")'><img src='../imgs/archive-icon.png'></img></a> <i>"+completion+"%</i><br></div>";
		}
	}
	projList.innerHTML = output;
}

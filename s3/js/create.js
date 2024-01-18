function processCreateResponse(result) {
	console.log("result:" + result);
	var js = JSON.parse(result);
	var pname = js.list[0]["name"];
	var output = "<div id=\"const" + pname + "\"><b>" + pname + " <br></div>";
}

function getURLParameter() {
	var projList = document.getElementById('projectList');
	var sPageURL = window.location.search.substring(1);
	var urlName = sPageURL.split('=');
	var pname = decodeURI(urlName[1]);
	console.log(pname)
	var output = "<div id=\"projName" + pname + "\"><b>" + pname + " </b></div>";
	projList.innerHTML = output;
}

function handleCreateClick(e) {
	var form = document.addProj;
	var data = {};
	data["name"] = form.projectName.value;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", create_url, true);
	xhr.send(js);
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				var res = xhr.responseText;
				var js = JSON.parse(res);
				console.log(js);
				var pname = js["response"];
				console.log(pname)
				location.href='https://project-management-system.s3.amazonaws.com/html/project-view.html?name='+pname;
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
			}
		} else {
			processCreateResponse("N/A");
		}
	};
}

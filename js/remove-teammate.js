function processRemoveResponse(result) {
	console.log("removed :" + result);
	var urlCurr = window.location.search;
	var newurl = urlCurr.replace('&tname='+result,'');
	window.location.href = newurl;
	getTeammateList();
}

function processRemove(e) {
	var form = document.removeTeammate;
	var data = {};
	data["name"] = form.teammateNameR.value;
	var sPageURL = window.location.search.substring(1);
	var urlName = sPageURL.split('=');
	var pname = decodeURI(urlName[1]);
	data["projectName"] = pname;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", remove_mate_url, true);
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
				var project = js["project"];
				var output = "";
				var mateList = project.teammates;
				var teammateList = document.getElementById('teammateList');
				if (js["statusCode"] == 200) {
					location.reload();
				} else {
					window.alert("Teammate cannot be removed from the project.");
				}
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processDeleteResponse("N/A");
		}
	};
	xhr.send(null);
}

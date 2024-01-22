function processCreateResponse(js) {
	console.log("result:" + js);
	var teammateList = document.getElementById('teammateList');
	var tname = js["taskName"];
	var output = output + "<div id=\"teammate" + tname + "\">" +tname +"<br></div>";
	teammateList.innerHTML = output;
}

function getTeammateList() {
	var tname = "";
	var toid = "";
	var output ="";
	var teammateList = document.getElementById('teammateList');
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] === "tname") {
			tname = sParameterName[1];
			output = output + "<div id=\"teammate" + tname + "\">" +tname +"<br></div>";
		}
	}
	teammateList.innerHTML = output;
}

function handleAddTeammateClick(e) {
	var form = document.addTeammate;
	var data = {};
	data["name"] = form.teammateName.value;
	var sPageURL = window.location.search.substring(1);
	var urlName = sPageURL.split('=');
	var pname = decodeURI(urlName[1]);
	data["projectName"] = pname;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", add_mate_url, true);
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
				console.log(js);
				if (js["statusCode"] == 200) {
					var project = js["project"];
					var output = "";
					var mateList = project.teammates;
					var teammateList = document.getElementById('teammateList');
					var assignmentList = project.assignments;
					for(var i = 0; i < mateList.length; i++) {
						var teammate = mateList[i];
						console.log(teammate);
						var tasks = "";
						var name = teammate.name;
						for (var j =0; j<assignmentList.length; j++) {
							if (Object.keys(assignmentList[j]).length !== 1) {
								var varName= assignmentList[j].teammate.name;
								if (varName === name) {
									var task = assignmentList[j].task;
									var outlineId = task.outlineIdentifier;
									var taskName = task.name;
									var isMarked = task.isMarked;
									var taskid = task.id;
									if (!isMarked) {
										tasks=tasks+"<p style='margin-left: "+40+"px'>"+outlineId+". "+taskName+"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='../imgs/gray-check.png'></img></a></p>";
									} else {
										tasks=tasks+"<p style='margin-left: "+40+"px'>"+outlineId+". "+taskName+"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='../imgs/green-check.png'></img></a></p>";
									}
								}
							}
						}
						console.log("teammate name = " + name);
						output = output + "<div id=\"task" + name + "\">" + name +"<br> <p style='margin-left: "+40+"px'>"+tasks+"</p></div>";
					}
					teammateList.innerHTML = output;
				} else {
					window.alert("Teammate cannot be added to the project.");
				}
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

function processCreateResponse(js) {
	console.log("result:" + js);
	var taskList = document.getElementById('taskList');
	var tname = js["taskName"];
	var toid = js["outlineIdentifier"];
	var output = output + "<div id=\"task" + toid + "\">" + toid + ". " + tname +"<br></div>";
	taskList.innerHTML = output;
}

function getTaskList() {
	var tname = "";
	var toid = "";
	var output = "";
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] === "taskName") {
			tname = sParameterName[1];
		} else if(sParameterName[0] === "outlineIdentifier") {
			toid = sParameterName[1];
			output = output + "<div id=\"task" + toid + "\">" + toid + ". " + tname +"<br></div>";
		}
	}
	taskList.innerHTML = output;
}

function handleAddTaskClick(e) {
	var form = document.addTask;
	var data = {};
	data["taskName"] = form.taskName.value;
	var sPageURL = window.location.search.substring(1);
	var urlName = sPageURL.split('=');
	var pname = decodeURI(urlName[1]);
	data["projectName"] = pname;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", add_task_url, true);
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
				console.log(js)
				var project = js["project"];
				var output = "";
				var taskList = project.tasks;
				var taskListGui = document.getElementById('taskList');
				var assignmentList = project.assignments;
				if (js["statusCode"] == 200) {
					for (var i = 0; i < taskList.length; i++) {
						var task = taskList[i];
						console.log(task);
						var outlineid = task.outlineIdentifier;
						var name = task.name;
						var taskid = task.id;
						var isMarked = task.isMarked;
						var hasSub = task.hasSubtasks;
						var count = outlineid.split('.');
						var tabs = count.length-1;
						console.log("outlineid = " + outlineid + " name = " + name);
						var teammates = "";
						for (var j =0; j<assignmentList.length; j++){
							if (Object.keys(assignmentList[j]).length !== 1){
								var varTaskID = assignmentList[j].task.id;
								if (varTaskID === taskid) {
									var mate = assignmentList[j].teammate.name;
									if (!isMarked) {
										teammates=teammates+"<p style='margin-left: "+40*(tabs+1)+"px'>"+mate+"<input type='button' value='Unassign' onClick='javaScript:promptUnassign(\""+taskid+"\",\""+mate+"\",this)'></p>";
									} else {
										teammates=teammates+"<p style='margin-left: "+40*(tabs+1)+"px'>"+mate+"</p>";
									}
								}
							}
						}
						if (!hasSub) {
							if (!isMarked) {
								output = output + "<div id=\"" + taskid + "\"> <p style='margin-left: "+40*tabs+"px'>" + outlineid + ". " + name +"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='gray-check.png'></img></a> <input type='button' value='Rename' onClick='javaScript:promptRename(\""+taskid+"\",this)'> "+"<input type='button' value='Decompose' onClick='javaScript:promptDecompose(\""+taskid+"\",this)'><input type='button' value='Assign' onClick='javaScript:promptAssign(\""+taskid+"\",this)'><br></p> <p style='margin-left: "+40*(tabs+1)+"px'>"+teammates+"</p> </div>";
							} else {
								output = output + "<div id=\"" + taskid + "\"> <p style='margin-left: "+40*tabs+"px'>" + outlineid + ". " + name +"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='green-check.png'></img></a> <input type='button' value='Rename' onClick='javaScript:promptRename(\""+taskid+"\",this)'> <br></p>"+"<p style='margin-left: "+40*(tabs+1)+"px'>"+teammates+"</p></div>";
							}
						} else {
							output = output + "<div id=\"" + taskid + "\"> <p style='margin-left: "+40*tabs+"px'>" + outlineid + ". " + name +"  <input type='button' value='Rename' onClick='javaScript:promptRename(\""+taskid+"\",this)'><br></p></div>";
						}
					}
					taskListGui.innerHTML = output;
				} else {
					window.alert("Task cannot be added to the project.");	
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

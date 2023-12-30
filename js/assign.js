function processAssignResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("Assign :" + result);
  
  location.reload();
}
function promptAssign(id, e){
	let name = window.prompt("Input teammate name:");
	var sPageURL = window.location.search.substring(1);
	var urlName = sPageURL.split('=');
	var pname = decodeURI(urlName[1]);
	processAssign(id, name, pname);
}



function processAssign(id, name, pname) {
	

 
  var data = {};
	data["teammateName"]=name;
  data["taskId"] = id;
	
	data["projectName"]=pname;
	
	//pname = data["name"];

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", assign_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			var res = xhr.responseText;
			var js = JSON.parse(res);
			  console.log ("XHR:" + xhr.responseText);
			if(js["statusCode"]==200){
			  processAssignResponse(xhr.responseText);
			}else{
				window.alert("Teammate cannot be assigned to task.");	
			}
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processAssignResponse("N/A");
	  }
  };
  
  xhr.send(null);  //  NEED TO GET IT GOING
}


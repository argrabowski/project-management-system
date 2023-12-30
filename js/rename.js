function processRenameResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("rename :" + result);
  
  location.reload();
}
function promptRename(id, e){
	let newName = window.prompt("Rename task to:");
	processRename(id, newName);
}


function requestRename(val, name) {
   if (confirm("Request to mark task ")) {
     processRename(val, name);
   }
}

function processRename(id, newName) {
	

 
  var data = {};
  data["id"] = id;
	data["newName"]=newName;
	
	//pname = data["name"];

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", rename_url, true);

  // send the collected data as JSON
  xhr.send(js);

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
			var res = xhr.responseText;
			var js = JSON.parse(res);
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			if(js["statusCode"]==200){
			  processRenameResponse(xhr.responseText);
			}else{
				window.alert("Task cannot be renamed.");	
			}
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processRenameResponse("N/A");
	  }
  };
  
 // xhr.send(null);  //  NEED TO GET IT GOING
}


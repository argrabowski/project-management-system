function processMarkResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("mark :" + result);
  
  location.reload();
}

function requestMark(val) {
   if (confirm("Request to change task marking")) {
     processMark(val);
   }
}

function processMark(id) {
	

 
  var data = {};
  data["id"] = id;
	
	//pname = data["name"];

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", mark_url, true);

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
			  processMarkResponse(xhr.responseText);
			}else{
				window.alert("Task completion status cannot me changed.");	
			}
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processMarkResponse("N/A");
	  }
  };
  
  xhr.send(null);  //  NEED TO GET IT GOING
}


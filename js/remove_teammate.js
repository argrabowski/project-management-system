function processRemoveResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("removed :" + result);
	var urlCurr = window.location.search;
	var newurl = urlCurr.replace('&tname='+result,'');
	window.location.href = newurl;
  
  	getTeammateList();
}

/*function requestRemove(val) {
   if (confirm("Request to remove " + val)) {
     processRemove(val);
   }
}*/

function processRemove(e) {
	var form = document.removeTeammate;
 
  var data = {};
  data["name"] = form.teammateNameR.value;
	//pname = data["name"];
	
	    var sPageURL = window.location.search.substring(1);
	    var urlName = sPageURL.split('=');
		
	       
		//var pname = urlName[1];
		var pname = decodeURI(urlName[1]);
	data["projectName"] = pname;

	var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", remove_mate_url, true);    // ISSUE with POST v. DELETE in CORS/API Gateway
	xhr.send(js);
  // This will process results and update HTML as appropriate. 
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
				if(js["statusCode"] == 200){
					/*for(var i = 0; i < mateList.length; i++) {
						var teammate = mateList[i];
						console.log(teammate);
						
						var name = teammate.name;
						console.log("teammate name = " + name);
						output = output + "<div id=\"task" + name + "\">" + name +"<br></div>";
					}
				 	teammateList.innerHTML = output;*/
					location.reload();
				}else{
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
  
  xhr.send(null);  //  NEED TO GET IT GOING
}


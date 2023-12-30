function processCreateResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result);
	
	var js = JSON.parse(result);
	var pname = js.list[0]["name"];
	var output = "<div id=\"const" + pname + "\"><b>" + pname + " <br></div>";
 // refreshProjectsList();
}

function getURLParameter()
	{
		var projList = document.getElementById('projectList');

	    var sPageURL = window.location.search.substring(1);
	    var urlName = sPageURL.split('=');
		
		
	       
		var pname = decodeURI(urlName[1]);
		
		//if(pname.includes("%20")){
		//nameHolder = pname.split("%20");
		//pname = nameHolder[0]+" "+nameHolder[1];
		//}
	    
	       
		console.log(pname)
	    var output = "<div id=\"projName" + pname + "\"><b>" + pname + " </b></div>";
	    projList.innerHTML = output;
	    
	}

function handleCreateClick(e) {
 
  var form = document.addProj;
 
  var data = {};
  data["name"] = form.projectName.value;
	
	//pname = data["name"];

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", create_url, true);

  // send the collected data as JSON
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
			
			var pname = js["response"];
			console.log(pname)
			location.href='https://3733projectappstabit.s3.us-east-2.amazonaws.com/html/index.html?name='+pname; 
			//embed id as a questionmark string
																										//extract query param from a webpage html
																										//another api
																										//on load queary
	      //processCreateResponse(xhr.responseText);
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

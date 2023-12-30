function processCreateResponse(js) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + js);
	
	//var js = JSON.parse(result);
	var teammateList = document.getElementById('teammateList');
	var tname = js["taskName"];
	var output = output + "<div id=\"teammate" + tname + "\">" +tname +"<br></div>";
	teammateList.innerHTML = output;
 // refreshProjectsList();
}


function getTeammateList()
	{
	  var tname = "";
		var toid = "";
		var output ="";
		var teammateList = document.getElementById('teammateList');
		var sPageURL = window.location.search.substring(1);
  		 var sURLVariables = sPageURL.split('&');
  		 for (var i = 0; i < sURLVariables.length; i++)
   			{
    			 var sParameterName = sURLVariables[i].split('=');
     			if (sParameterName[0] === "tname")
     			{
					tname = sParameterName[1];
					output = output + "<div id=\"teammate" + tname + "\">" +tname +"<br></div>";
				}
				
   			}
	    //var projList = document.getElementById('projectList');
	       
		//var pname = urlName[1];
		//console.log(pname)
	    //var output = "<div id=\"projName" + pname + "\"><b>" + pname + " </b></div>";
	    teammateList.innerHTML = output;
	    
	}

function handleAddTeammateClick(e) {
 
  var form = document.addTeammate;
 
  var data = {};
  data["name"] = form.teammateName.value;
	//pname = data["name"];
	
	    var sPageURL = window.location.search.substring(1);
	    var urlName = sPageURL.split('=');
		
	       
		//var pname = urlName[1];
		var pname = decodeURI(urlName[1]);
	data["projectName"] = pname;

  var js = JSON.stringify(data);
  console.log("JS:" + js);
  var xhr = new XMLHttpRequest();
  xhr.open("POST", add_mate_url, true);

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
			
			//var task = js["response"];
			console.log(js)
			//processCreateResponse(js);
			if(js["statusCode"] == 200){
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
					
					for(var j =0; j<assignmentList.length; j++){
							if(Object.keys(assignmentList[j]).length !== 1){
								var varName= assignmentList[j].teammate.name;
								
								if(varName === name){
									var task = assignmentList[j].task;
									var outlineId = task.outlineIdentifier;
									var taskName = task.name;
									var isMarked = task.isMarked;
									var taskid = task.id;
									if(!isMarked){
										tasks=tasks+"<p style='margin-left: "+40+"px'>"+outlineId+". "+taskName+"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='gray_check.png'></img></a></p>";
									}else{
										tasks=tasks+"<p style='margin-left: "+40+"px'>"+outlineId+". "+taskName+"<a href='javaScript:requestMark(\"" + taskid + "\")'><img src='green_check.png'></img></a></p>";
									}
								}
						}
				}
					console.log("teammate name = " + name);
					output = output + "<div id=\"task" + name + "\">" + name +"<br> <p style='margin-left: "+40+"px'>"+tasks+"</p></div>";
				}
				
			 	teammateList.innerHTML = output;
			}else{
				window.alert("Teammate cannot be added to the project.");	
			}
			
			//location.href='https://3733projectappstabit.s3.us-east-2.amazonaws.com/html/index.html?name='+pname; 
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

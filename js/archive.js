function processArchiveResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("archived :" + result);
  
  refreshProjectsList();
}

function requestArchive(val) {
   if (confirm("Request to archive " + val)) {
     processArchive(val);
   }
}

function processArchive(name) {

  var xhr = new XMLHttpRequest();
  xhr.open("POST", archive_url+ "/?projectname=" + name, true);    // ISSUE with POST v. DELETE in CORS/API Gateway

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
	  console.log(xhr);
	  console.log(xhr.request);
	  if (xhr.readyState == XMLHttpRequest.DONE) {
		  if (xhr.status == 200) {
			  console.log ("XHR:" + xhr.responseText);
			  processArchiveResponse(xhr.responseText);
		  } else {
			  console.log("actual:" + xhr.responseText)
			  var js = JSON.parse(xhr.responseText);
			  var err = js["error"];
			  alert (err);
		  }
	  } else {
		  processArchiveResponse("N/A");
	  }
  };
  
  xhr.send(null);  //  NEED TO GET IT GOING
}


function goToTeammateView()
	{
		var sPageURL = window.location.search.substring(1);
  		// var sURLVariables = sPageURL.split('&');

		location.href='https://3733projectappstabit.s3.us-east-2.amazonaws.com/html/teammateview.html?' + sPageURL; 
	}
	
function goToProjectView()
	{
		var sPageURL = window.location.search.substring(1);
  		// var sURLVariables = sPageURL.split('&');

		location.href='https://3733projectappstabit.s3.us-east-2.amazonaws.com/html/index.html?' + sPageURL; 
	}
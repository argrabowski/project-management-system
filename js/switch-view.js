function goToTeammateView() {
	var sPageURL = window.location.search.substring(1);
	location.href='https://project-management-system.s3.amazonaws.com/html/team-view.html?' + sPageURL;
}

function goToProjectView() {
	var sPageURL = window.location.search.substring(1);
	location.href='https://project-management-system.s3.amazonaws.com/html/project-view.html?' + sPageURL;
}

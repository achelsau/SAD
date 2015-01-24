(function startup() {
	function populateControls() {
		$.ajax({
			type : "POST",
			url: "http://localhost:8080/SmartAdmin/sad/getEntities/",
			contentType : "text/plain",
		}).done(handlePopulationWithPersistentQueries);
	}
	
	window.onload = function() {
		populateControls();
	}
}())
(function startup() {
	var dataToUse = null;
	function populateControls() {
		$.ajax({
			type : "GET",
			url: "http://localhost:8080/SmartAdmin/sad/entities/",
			contentType : "text/plain",
		}).done(handlePopulateControls);
	}
	
	function handlePopulateControls(data) {
		dataToUse = data;
		
		$('#softwareToChoose').find('option').remove();
		for (var key in data) {
			$("#softwareToChoose").append("<option value=" + key + ">" + key + "</option>");
		}
		
		$("#softwareToChoose").change(function() {
			var softwareDescription = data[$("#softwareToChoose option:selected").text()]
			
			var versions = softwareDescription.versions;
			$('#softwareVersion').find('option').remove();
			for (var i = 0; i < versions.length; i++) {
				$("#softwareVersion").append("<option value=" + versions[i] + ">" + versions[i] + "</option>");
			}
			
			var operatingSystems = softwareDescription.operatingSystems;
			$('#operatingSystem').find('option').remove();
			for (var i = 0; i < operatingSystems.length; i++) {
				$("#operatingSystem").append("<option value=" + operatingSystems[i] + ">" + operatingSystems[i] + "</option>");
			}
			
			var architectures = softwareDescription.architectures;			
			$('#operatingSystemArchitecture').find('option').remove();
			for (var i = 0; i < architectures.length; i++) {
				$("#operatingSystemArchitecture").append("<option value=" + architectures[i] + ">" + architectures[i] + "</option>");
			}
		});
	}
	
	window.onload = function() {
		populateControls();
	}
}())
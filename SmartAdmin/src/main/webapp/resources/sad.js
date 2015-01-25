(function startup() {
	var dataToUse = null;
	function populateControls() {
		$.ajax({
			type : "GET",
			url: "http://localhost:8080/SmartAdmin/sad/entities/",
			contentType : "text/plain",
		}).done(handlePopulateControls);
		
		$("#computeDependencies").click(function() {
			$("#right").empty();
			
			var name = $("#softwareToChoose option:selected").text().split(" ").join("_")
			var version = $("#softwareVersion option:selected").text()
			var os = $("#operatingSystem option:selected").text()
			var osArch = $("#operatingSystemArchitecture option:selected").text()
			
			var completeName = name + ((version === "none") ? "_" : "_" + version + "_") + os + "_" + osArch;
			
			$.ajax({
				type : "POST",
				url: "http://localhost:8080/SmartAdmin/sad/query/",
				contentType : "text/plain",
				data: completeName
			}).done(showDependenciesAndAttachEvents);
		});
	}
	
	function showDependenciesAndAttachEvents(data) {
		showDependencyTree(data, 0);
		
		$(".dependencyElement").click(function() {
			$("#popup").show();
			$("#popup").draggable();
		})
	}
	
	function showDependencyTree(data, margin) {
		addDependencyToTree(data.userFriendlyName + " " + ((data.version != "none") ? data.version : ""), margin);
		
		if (data.dependsOn.length > 0) {
			for (var i = 0; i < data.dependsOn.length; i++) {
				showDependencyTree(data.dependsOn[i], margin + 20);
			}
		}
	}
	
	function addDependencyToTree(dependencyName, margin) {
		$("#right").append("<div class='dependencyElement' style=\"cursor: pointer; margin-left:" + margin + "px\">" 
				+ dependencyName + "</div>");
	}
	
	function handlePopulateControls(data) {
		dataToUse = data;
		
		$('#softwareToChoose').find('option').remove();
		for (var key in data) {
			$("#softwareToChoose").append("<option value=" + key + ">" + key + "</option>");
			
			populateSoftwareDescription();
		}
		
		$("#softwareToChoose").change(function() {
			populateSoftwareDescription();
		});
	}
	
	function populateSoftwareDescription() {
		var softwareDescription = dataToUse[$("#softwareToChoose option:selected").text()]
		
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
	}
	
	window.onload = function() {
		$("#popup").hide();
		$("#closeBtn").click(function() {
			$("#popup").hide();
		})
		
		populateControls();
	}
}())
var isDebugMode = false;
var isHelperMode = false;

//comment
function serviceCallGET() {
	var serviceName = document.getElementById("serviceList").value;
	var baseURL = getServiceBaseUrl();
	
    //alert("servicePath : " + document.getElementById("servicePath").value);
    var restServiceURL = baseURL + serviceName;
    if (isDebugMode) {
    	console.log("restServiceURL : " + restServiceURL);
    }
          
    var requestXML = document.getElementById("requestxml").value;
    if (isDebugMode) {
    	console.log("requestXML : " + requestXML);
    }
    
    var dataParamName = getDataParamNameForService(serviceName);
    if (isDebugMode) {
    	console.log("dataParamName : " + dataParamName);
    }
    
    var dataParam = dataParamName + "=" + requestXML;
    if (isDebugMode) {
    	console.log("dataParam : " + dataParam);
    }
            
    $('#messages').empty();
    toggleProcessModal(true);
    $.ajax({
        type: "GET",
        url: restServiceURL ,
        data: dataParam,
        secureuri: false,
        dataType: "text",
        success: function (data){ 
	        //alert(data);
            document.getElementById("responsexml").value = data;
            toggleProcessModal(false);
        },
    	error: function (data, status, e) {
        	alert("failure");
        	$('#messages').html("data: " + JSON.stringify(data, undefined, 2) + "<br><br>status: " + status + "<br><br>error: " + e);
        	toggleProcessModal(false);
        }
	});
};

//comment
function serviceCallPOST() {
	var serviceName = document.getElementById("serviceList").value;
	var baseURL = getServiceBaseUrl();
	
    //alert("servicePath : " + document.getElementById("servicePath").value);
    var restServiceURL = baseURL + serviceName;
    if (isDebugMode) {
    	console.log("restServiceURL : " + restServiceURL);
    }
          
    var requestXML = document.getElementById("requestxml").value;
    if (isDebugMode) {
    	console.log("requestXML : " + requestXML);
    }
            
    $('#messages').empty();
    toggleProcessModal(true);
    $.ajax({
        type: "POST",
        url: restServiceURL ,
        data: requestXML,
        secureuri: false,
        dataType: "text",
        contentType: 'application/json',
        success: function (data){ 
	        //alert(data);
            document.getElementById("responsexml").value = data;
            toggleProcessModal(false);
        },
    	error: function (data, status, e) {
        	alert("failure");
        	$('#messages').html("data: " + JSON.stringify(data, undefined, 2) + "<br><br>status: " + status + "<br><br>error: " + e);
        	toggleProcessModal(false);
        }
	});
};

//comment
function getURL() {
	if (window.location) {
		return decodeURIComponent(window.location);	
	}
}

function getOrigin() {
	var baseURL;
	
	if (!window.location.origin) {
		baseURL = window.location.protocol + "//" + window.location.host;
	} else {
		baseURL = window.location.origin;
	}
	
	return baseURL;
}

function getServiceBaseUrl() {
	return getOrigin() + "/service/";
}

function getDataParamNameForService(serviceName) {
	if (isDebugMode) {
    	console.log("serviceName : " + serviceName);
    }
	if (serviceName == "service1") {
		return "service1Param";
	} else if (serviceName == "service2") {
		return "service2Param";
	}
}

//function to display processing modal
function toggleProcessModal(toggleOn) {
	if(toggleOn){
		$('#processingModal').modal('show');
	} else{
		$('#processingModal').modal('hide');
	}
}

//Very first load to page these things must occur
$(document).ready(function(e){
	$('#servicePathRoot').val(getServiceBaseUrl());
	swapSimpleAndComplex();
});

$(document.body).on('change','#serviceList', function(e){
	swapSimpleAndComplex();
});

function swapSimpleAndComplex() {
	var serviceName = $('#serviceList').val();
	
	if (serviceName == "service1" || serviceName == "service2") {
		$("#request-response-container").load('rest_request_complex.html', function(e) {
			if (isHelperMode) {
				if(serviceName != '') {
					getExampleForService(serviceName);
				}
			}
			if (serviceName == "service2") {
				$('#executeButton').attr('onclick', 'serviceCallPOST()');
			} else {
				$('#executeButton').attr('onclick', 'serviceCallGET()');
			}
			
			$('#helperModeCheckbox').removeAttr('disabled');
		}); 
	} else {
		$("#request-response-container").load('rest_request_simple.html', function(e) {
			if (isHelperMode) {
				if(serviceName != '') {
					getExampleForService(serviceName);
				}
			}
			$('#executeButton').attr('onclick', 'simpleServiceCallGET()');
			$('#helperModeCheckbox').attr('disabled', true);
		}); 
	}
}

// <-------------------------- start helper mode -------------------------->

$(document.body).on('change','#helperModeCheckbox', function(e){
	if ($('#helperModeCheckbox').is(':checked')) {
		isHelperMode = true;
		getExampleForService($('#serviceList').val());
	} else {
		isHelperMode = false;
		document.getElementById("requestxml").value = "";
	}
});

function getExampleForService(serviceName) {
	if (isDebugMode) {
    	console.log("serviceName : " + serviceName);
    }
	if (document.getElementById("requestxml") != null) {
		if (serviceName == "service1") {
			document.getElementById("requestxml").value = JSON.stringify(service1Example, undefined, 2);
		} else if (serviceName == "service2") {
			document.getElementById("requestxml").value = JSON.stringify(service2Example, undefined, 2);
		} else {
			document.getElementById("requestxml").value = "";
		}
	}
}

// helper mode examples

var service1Example =
{
	"profileInfo" : {
		"profileId" : "bConnected"
	},
	"year" : "2015",
	"zipCode" : "80210",
	"planCategory" : "MAPD",
	"county" : { 
		"fipsCountyCode" : "031",
		"fipsCountyName" : "Denver County",
		"fipsStateCode" : "08",
		"stateCode" : "CO", 
		"cmsCountyCodes" : ["150"] 
	}, 
	"includeDrugBenefit" : true,
	"includeMedicalBenefit" : false
};

var service2Example =
{
  "profileInfo" : {
    "profileId" : "bConnected",
    "persistenceId" : "test20140723",
    "userId" : null,
    "callbackURL" : null,
    "agentId" : null
  },
  "userDrugs" : {
    "userDrugList" : [ {
      "drugId" : "13217",
      "nationalDrugCode" : "00078045805",
      "drugLabel" : null,
      "year" : "2014",
      "quantity" : "60",
      "frequency" : "30"
    } ]
  }
};


//<-------------------------- end helper mode -------------------------->
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
	return getOrigin() + "/DCERestWAR/dcerest/";
}

function getDataParamNameForService(serviceName) {
	if (isDebugMode) {
    	console.log("serviceName : " + serviceName);
    }
	if (serviceName == "estimateplancost") {
		return "estimatePlanCost";
	} else if (serviceName == "lowcostoptions") {
		return "lowCostCriteria";
	} else if (serviceName == "searchpharmacy") {
		return "pharmacySearch";
	} else if (serviceName == "searchplans") {
		return "planSearchCriteria";
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
	
	if (serviceName == "estimateplancost" || serviceName == "lowcostoptions" || serviceName == "searchpharmacy" || serviceName == "searchplans" || serviceName == "saveuserdruglist") {
		$("#request-response-container").load('rest_request_complex.html', function(e) {
			if (isHelperMode) {
				if(serviceName != '') {
					getExampleForService(serviceName);
				}
			}
			if (serviceName == "saveuserdruglist") {
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
		if (serviceName == "estimateplancost") {
			document.getElementById("requestxml").value = JSON.stringify(estimateplancostExample, undefined, 2);
		} else if (serviceName == "lowcostoptions") {
			document.getElementById("requestxml").value = JSON.stringify(lowcostoptionsExample, undefined, 2);
		} else if (serviceName == "searchpharmacy") {
			document.getElementById("requestxml").value = JSON.stringify(searchpharmacyExample, undefined, 2);
		} else if (serviceName == "searchplans") {
			document.getElementById("requestxml").value = JSON.stringify(searchplansExample, undefined, 2);
		} else if (serviceName == "saveuserdruglist") {
			document.getElementById("requestxml").value = JSON.stringify(saveuserdruglistExample, undefined, 2);
		} else {
			document.getElementById("requestxml").value = "";
		}
	}
}

// helper mode examples

var estimateplancostExample = 
{
	"planStartMonth":"8",
	"zipCode":"90210",
	"county":
	{
		"fipsCountyCode":"037",
		"fipsCountyName":"Los Angeles County",
		"fipsStateCode":"06",
		"stateCode":"CA",
		"cmsCountyCodes":["200","210"]
	},
	"brandId":"AARP",
	"accumulatedTotalDrugCost":null,
	"accumulatedTroopBalance":null,
	"copayCategory":"0",
	"plans":[
		{
			"contractId":"S5820",
			"pbpNumber":"031",
			"segmentId":null,
			"planYear":"2014"
		},
		{
			"contractId":"S5921",
			"pbpNumber":"003",
			"segmentId":null,
			"planYear":"2014"
		},
		{
			"contractId":"S5921",
			"pbpNumber":"376",
			"segmentId":null,
			"planYear":"2014"
		}
	],
	"pharmacies":[
		{
			"pharmacyNumber":"557112",
			"mailOrder":false,
			"pharmacySaver":true
		}
	],
	"drugs":[
		{
			"nationalDrugCode":"00186074368",
			"drugQuantity":"30",
			"drugFrequency":"30",
			"packageQuantity":null,
			"packageSize":null
		},
		{
			"nationalDrugCode":"55513071001",
			"drugQuantity":"1",
			"drugFrequency":"30",
			"packageQuantity":"1",
			"packageSize":"1"
		},
		{
			"nationalDrugCode":"00187065142",
			"drugQuantity":"48",
			"drugFrequency":"30",
			"packageQuantity":null,
			"packageSize":null
		}
	]
};

var lowcostoptionsExample = 
{
	"profileInfo" : {
		"profileId" : "bConnected",
		"persistenceId" : "",
		"userId" : "",
		"agentId" : ""
	},
	"basicInfo" : {
		"planStartMonth" : "8",
		"zipCode" : "90210",
		"county" : {
			"fipsCountyCode" : "037",
			"fipsCountyName" : "Los Angeles County",
			"fipsStateCode" : "06",
			"stateCode" : "CA",
			"cmsCountyCodes" : [ "200", "210" ]
		},
		"brandId" : "AARP",
		"accumulatedTotalDrugCost" : null,
		"accumulatedTroopBalance" : null,
		"copayCategory" : "0",
		"planCategory" : "PDP",
		"totalAnnualDrugCost" : "400.00"
	},
	"selectedPlan" : {
		"contractId" : "S5820",
		"pbpNumber" : "031",
		"segmentId" : null,
		"planYear" : "2014"
	},
	"selectedPharmacySearchCriteria" : {
		"pharmacyAddress" : {
			"addressLine1" : null,
			"addressLine2" : null,
			"city" : null,
			"state" : null,
			"zipCode" : "90210",
			"phoneNumber" : null,
			"tty" : null
		},
		"yearFilter" : 2014,
		"paginationIndex" : 1,
		"maxRecordPerPage" : 50,
	},
	"selectedPharmacy" : {
		"pharmacyNumber" : "0594855",
		"mailOrder" : false,
		"preferred" : false,
		"pharmacySaver" : false
	},
	"selectedDrugs" : [ {
		"nationalDrugCode" : "63304082790",
		"drugQuantity" : "30",
		"drugFrequency" : "30",
		"packageQuantity" : null,
		"packageSize" : null
	}, {
		"nationalDrugCode" : "00378615001",
		"drugQuantity" : "30",
		"drugFrequency" : "30",
		"packageQuantity" : null,
		"packageSize" : null
	}]
};
	

var searchpharmacyExample = 
{
	"profileInfo" : null,
	"pharmacySearchCriteria" : {
		"pharmacyAddress" : {
			"addressLine1" : null,
			"addressLine2" : null,
			"city" : null,
			"state" : null,
			"zipCode" : "90210"
		},
		"radius" : "5",
		"pharmacyNameFilter" : "GARFIELD",
		"filtersInclusive" : true,
		"pharmacySearchFilters" : {
			"twentyFourHourTypeFilter" : null,
			"ninetyDayTypeFilter" : null,
			"longTermCareTypeFilter" : null,
			"specialtyTypeFilter" : null,
			"retailTypeFilter" : null,
			"indianTribalUnionFilter" : null,
			"mailOrderTypeFilter" : null,
			"eprescriptionFilter" : null,
			"pharmacySaverFilter" : null,
			"preferredNetworkFilter" : null
		},
		"yearFilter" : "2014",
		"paginationIndex" : "1",
		"maxRecordPerPage" : "10",
		"ndcFilter" : null,
		"compositePlanId" : null
	}
};

var searchplansExample = 
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

var saveuserdruglistExample = 
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
var htmlParser = require("cloud/htmlparser.js");
var path = "http://m.dining.ucla.edu/menu/index.cfm?";

Parse.Cloud.define("populateDiningHallMenus", function(request,response){
	Parse.Cloud.httpRequest({
		url: "http://m.dining.ucla.edu/menu/index.cfm?restaurantType=Residential"
	}).then(function(httpResponse) {
		var html = httpResponse.text;
		//to test if it works?
		var test = new Parse.Object("CloudTest");
		test.add("test", httpResponse.text);
		console.log(httpResponse.text);
		test.save();

		//breakfast
		var bfast_re = new RegExp(/<div id="nav">(\s)<a href="(>[Breakfast])/);
		var bfastOut = bfast_re.exec(httpResponse.text);
		console.log(bfastOut[1]);

		//lunch
		var lunch_re = new RegExp(/<div id="nav">(\s)<a href="(>[Lunch])/);

		//dinner
		var din_re = new RegExp(/<div id="nav">(\s)<a href="(>[Dinner])/);


  	  	outputText += ' }';
    	var outputJSON = JSON.parse(outputText);
  		console.log(outputJSON);
  		
	}, function(HttpResponse) {
		console.error('request failed dawg');
	});
});
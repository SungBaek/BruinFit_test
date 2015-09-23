// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
var path = "http://m.dining.ucla.edu/menu/index.cfm?";
var mealPath = "http://m.dining.ucla.edu/menu/";
var homePath = "http://m.dining.ucla.edu/menu/index.cfm?restaurantType=Residential";
  
function getNutrients(http, dining, time){
  var promise = new Parse.Promise();
  Parse.Cloud.httpRequest({
    url: http
  }).then(function(httpResponse) {
    //success
    var html = httpResponse.text;

    //Parse object initiation
    var ObjClass = Parse.Object.extend("Nutrients");
    var obj = new ObjClass();

    //dining hall
    obj.set("diningHall", dining);
    //break,lunch, or dinner?
    obj.set("time", time);
    //calorie
    var cal_re = new RegExp(/Calories:\<\/strong\>\s([0-9\.]*)/); 
    var calOut = cal_re.exec(html);
    obj.set("calorie", calOut[1]);
    //name
    var name_re = new RegExp(/Nutritional information<br\/>$\s*(.*)$/m);
    var nameOut = name_re.exec(html);
    obj.set("name", nameOut[1]);
    //calorie from fat
    var calFromFat_re = new RegExp(/Calories\sfrom\sFat:\s([0-9\.]*)/);
    var calFromFatOut = calFromFat_re.exec(html);
    obj.set("fatCalorie", calFromFatOut[1]);
    //Total fat
    var totFat_re = new RegExp(/Total\sFat:\<\/strong\>\s([0-9\.]*)/);
    var totFatOut = totFat_re.exec(html);
    obj.set("totFat", totFatOut[1]);
    //saturated fat
    var satFat_re = new RegExp(/Saturated\sFat:\s([0-9\.]*)/);
    var satFatOut = satFat_re.exec(html);
    obj.set("satFat", satFatOut[1]);
    //Trans fat
    var transFat_re = new RegExp(/Trans\sFat:\s([0-9\.]*)/);
    var transFatOut = transFat_re.exec(html);
    obj.set("transFat", transFatOut[1]);
    //cholesterol
    var chol_re = new RegExp(/Cholesterol:\<\/strong\>\s([0-9\.]*)/);
    var cholOut = chol_re.exec(html);
    obj.set("chol", cholOut[1]);
    //sodium
    var sod_re = new RegExp(/Sodium:\<\/strong\>\s([0-9\.]*)/);
    var sodOut = sod_re.exec(html);
    obj.set("sod", sodOut[1]);
    //Total Carbohydrate:
    var totCarb_re = new RegExp(/Total\sCarbohydrate:\<\/strong\>\s([0-9\.]*)/);
    var totCarbOut = totCarb_re.exec(html);
    obj.set("carb", totCarbOut[1]);
    //Dietary Fiber: 
    var fib_re = new RegExp(/Dietary\sFiber:\s([0-9\.]*)/);
    var fibOut = fib_re.exec(html);
    obj.set("fiber", fibOut[1]);
    //sugar
    var sug_re = new RegExp(/Sugars:\s([0-9\.]*)/);
    var sugOut = sug_re.exec(html);
    obj.set("sugar", sugOut[1]);
    //protein
    var prot_re = new RegExp(/Protein:\<\/strong\>\s([0-9\.]*)/);
    var protOut = prot_re.exec(html);
    obj.set("protein", protOut[1]);

    promise.resolve(obj); //obj gets passed into promise which is returned.
      //save here

    }, function(HttpResponse) {
    console.error('couldn\'t get nutrient');
    promise.reject("nutrient promise failed");
  });
  return promise;
}


function extractMeal(http, dining, time){
  var promise = new Parse.Promise();
  Parse.Cloud.httpRequest({
    url: http
  }).then(function(httpResponse){
    var re = new RegExp(/nutritional\.cfm\?[^"]*/g);
    var output = re.exec(httpResponse.text);
    var promiseArray = [];

    //hold all parse object elements then save all of them at once.
    while (output != null){
        var outputPath = mealPath + output;
        promiseArray.push(getNutrients(outputPath, dining, time)); //promise gets pushed
        output = re.exec(httpResponse.text); //grab next element
    }
    //if promisearray has been resolved then create an array of arguments then save it all.
    Parse.Promise.when(promiseArray).then(function(){
      var objHolder = [];
      for(i = 0; i < promiseArray.length; i++){ 
        //arguments[i] will grab the ith argument, which is the obj of ith.
        objHolder.push(arguments[i]);
        if(i == 0){
          //sanity check
        console.log(arguments[i].toJSON()); 
        }
      }
      //put all the objects into obj holder.
      promise.resolve(objHolder);
    });
  }, function(HttpResponse) {
    console.error('extratMeal failed');
    promise.reject("extractmeal didn't work");
  });  
  return promise;
}

function grabDining(http, time){
    Parse.Cloud.httpRequest({
    url: http
  }).then(function(httpResponse) {
    var html = httpResponse.text;
    //to test if it works?

    console.log("Success in connecting to dining halls!");
    //create promise array and such.
    var promiseArray = [];
    //de neve
    var deneve_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>De\sNeve/);
    var deneveOut = deneve_re.exec(httpResponse.text);
    if( deneveOut !== null){
      //follow html here
      var denevePath = path + deneveOut[1];
      denevePath = path + denevePath;
      console.log("Deneve Output: " + deneveOut);
      promiseArray.push(extractMeal(denevePath, "deneve", time));
    }
    else{
      console.log("no deneve :(");
    }

    //covel
    var covel_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>Covel\sDining/);
    var covelOut = covel_re.exec(httpResponse.text);
    if( covelOut !== null){
      var covelPath = path + covelOut[1];
      console.log("covel output: " + covelPath);
      promiseArray.push(extractMeal(covelPath, "covel", time));
    }
    else{
      console.log("no covel :(");
    }
    //feast
    var feast_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>FEAST\sat\sRieber/);
    var feastOut = feast_re.exec(httpResponse.text);
    if ( feastOut !== null){
      var feastPath = path + feastOut[1];
      console.log("feast output: " + feastPath);
      promiseArray.push(extractMeal(feastPath, "feast", time));
    }
    else{
      console.log("no feast :(")
    }
    //grab everything and save here
    Parse.Promise.when(promiseArray).then(function(){
    var objHolder = [];
    for(i = 0; i < promiseArray.length; i++){ 
      //arguments[i] will grab the ith argument, which is the obj of ith.
      objHolder = objHolder.concat(arguments[i]);
    }
    return Parse.Promise.as(objHolder);
    }).then(function(objHolder){
      //save everything here
      return Parse.Object.saveAll(objHolder,{
          success:function (a) {
          console.log("we saved! " + a.length);
        },
        error:function(e) {
          console.log("didn't work" + e.message);
        }
      });
    });
    }, function(HttpResponse) {
    console.error(http + 'request failed dawg');
   });
  }

function grabGroup(http) {
  //count how many to figre out size of container
  //1,2,3
  Parse.Cloud.httpRequest({
    url: http
  }).then(function(httpResponse) {
    var countRe = new RegExp(/"menulocheader">([A-Za-z]*)</g);
    var countOutput = countRe.exec(httpResponse.text);
    var timeList = [];
    while(countOutput != null){
      countOutput = countRe.exec(httpResponse.text);
      //store the string, it indicates if it was "breakfast", "lunch", or "dinner"
      timeList.push(countOutput[1].toLowerCase());
    }
    //grab the "family"
    var index;
    var menuRe = new RegExp(//g);
    for (index = 0; index < timeList.length-1 ; index++){

    }
    //parse family
    //store family
    var index;

  }, function(httpResponse){

  });

  //grab meals from that section
  //loop by size of containers

  //save them
}

Parse.Cloud.job("getGroups", function(request,status){
   Parse.Cloud.httpRequest({
    url: "http"
  }).then(function(httpResponse) {
    //get covel

    //get deneve

    //get feast

    //get bruiplate


}, function(httpResponse){

  });
});

Parse.Cloud.job("jobtest", function(request,status){
  status.success("yay");
});

Parse.Cloud.job("doBreakfast", function(request,response){
Parse.Cloud.httpRequest({
    url:homePath
  }).then(function(httpResponse){
    //success
    var breakfastRe = new RegExp(/.*href="(.*Breakfast.*)".*/);
    var breakfastOut = breakfastRe.exec(httpResponse.text);
    var breakfastPath = mealPath + breakfastOut[1];
    breakfastPath = breakfastPath.replace(/ /g,"%20");
    console.log("breakfast following:" + breakfastPath);
    if (breakfastOut !== null){
      grabDining(breakfastPath, "breakfast");
    }
  }, function(httpResponse){
    //error
  });
});

Parse.Cloud.job("doLunch", function(request,response){
  Parse.Cloud.httpRequest({
    url:homePath
  }).then(function(httpResponse){
    //success
    var lunchRe = new RegExp(/.*href="(.*Lunch.*)"\s.*/);
    var lunchOut = lunchRe.exec(httpResponse.text);
    var lunchPath = mealPath + lunchOut[1];
    lunchPath = lunchPath.replace(/ /g,"%20")
    console.log("lunch follwing: " + lunchPath);
    if (lunchOut !== null){
      grabDining(lunchPath, "lunch");
    }
    else{
      console.log("can't grab lunch:(");
    }
  }, function(httpResponse){
    //error
  });
});

Parse.Cloud.job("doDinner", function(request,response){
  Parse.Cloud.httpRequest({
    url:homePath
  }).then(function(httpResponse){
    //success
    var dinnerRe = new RegExp(/.*href="(.*Dinner.*)".*/);
    var dinnerOut = dinnerRe.exec(httpResponse.text);
    var dinnerPath = mealPath + dinnerOut[1];
    dinnerPath = dinnerPath.replace(/ /g,"%20")
    console.log("dinner following: " + dinnerPath);
    if (dinnerOut !== null){
      grabDining(dinnerPath, "dinner");
    }
    else{
      console.log("can't grab dinner");
    }
  }, function(httpResponse){
    //error
  });
});

Parse.Cloud.define("populateDiningHallMenus", function(request,response){
  Parse.Cloud.httpRequest({
    url: "http://m.dining.ucla.edu/menu/index.cfm?restaurantType=Residential"
  }).then(function(httpResponse) {
    var html = httpResponse.text;
    //to test if it works?

    console.log("Success in connecting to dining halls!");
    //cloud test
  
    //de neve
    var deneve_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>De\sNeve/);
    var deneveOut = deneve_re.exec(httpResponse.text);
    if( deneveOut !== null){
      //follow html here
      var denevePath = path + deneveOut[1];
      denevePath = path + denevePath;
      console.log("Deneve Output: " + deneveOut);
      //extractMeal(denevePath);
    }
    else{
      console.log("no deneve :(");
    }

    //covel
    var covel_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>Covel\sDining/);
    var covelOut = covel_re.exec(httpResponse.text);
    if( covelOut !== null){
      var covelPath = path + covelOut[1];
      console.log("covel output: " + covelPath);
      extractMeal( covelPath );
    }
    else{
      console.log("no covel :(");
    }
    //feast
    var feast_re = new RegExp(/"visitorHeadings"\>\<a\shref="([^"]*)"\>FEAST\sat\sRieber/);
    var feastOut = feast_re.exec(httpResponse.text);
    if ( feastOut !== null){
      var feastPath = path + feastOut[1];
      console.log("feast output: " + feastPath);
      //extractMeal(feastPath);
    }
    else{
      console.log("no feast :(")
    }
      
  }, function(HttpResponse) {
    console.error('request failed dawg');
  });
});
//git commit -a
//:wq
//git push origin master
Parse.Cloud.define("flushNutrients", function(request,response){
  var query = new Parse.Query("Nutrients");
  query.each(function(obj){
    obj.destroy();
  });
});
Parse.Cloud.job("flushNutrientsJob", function(request,response){
  var query = new Parse.Query("Nutrients");
  query.each(function(obj){
    obj.destroy();
  }).then(function(){
    //status.success("destroyed all of them");
  },function(){
    //status.error("couldn't destroy them");
  });
});
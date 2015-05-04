var quizapp = angular.module('quizapp', [ 'ngRoute', 'ngResource', 'smart-table', 'ui.bootstrap']);

quizapp.run(function($rootScope) {
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;
});

//factory for sharing data between controller
quizapp.factory('dataSharing', function() {
	var sharedData = {}
	function set(data) {
		sharedData = data;
	}
	function get() {
		return sharedData;
	}

	return {
		set : set,
		get : get
	}

});

quizapp.directive('passwordMatch', [ function() {
	return {
		restrict : 'A',
		scope : true,
		require : 'ngModel',
		link : function(scope, elem, attrs, control) {
			var checker = function() {

				//get the value of the first password
				var e1 = scope.$eval(attrs.ngModel);

				//get the value of the other password  
				var e2 = scope.$eval(attrs.passwordMatch);
				return e1 == e2;
			};
			scope.$watch(checker, function(n) {

				//set the form control to valid if both 
				//passwords are the same, else invalid
				control.$setValidity("unique", n);
			});
		}
	};
} ]);

//configure our routes
quizapp.config(function($routeProvider) {
	$routeProvider

	// route for the welcome page
	.when('/', {
		templateUrl : 'home.html',
		controller : 'homeController'
	})

	.when('/register', {
		templateUrl : 'register.html',
		controller : 'registerController'
	})


	.when('/quizhome', {
		templateUrl : 'quizhome.html',
		controller : 'quizhomeController'
	})

	.when('/profile', {
		templateUrl : 'profile.html',
		controller : 'profileController'
	})

	.when('/globaldashboard', {
		templateUrl : 'globaldashboard.html',
		controller : 'globalDashboardController'
	})

	.when('/home', {
		templateUrl : 'userhome.html',
		controller : 'homeUserController'
	})

	.when('/createquiz', {
		templateUrl : 'createQuiz.html',
		controller : 'createquizController'
	})
	
	.when('/quizsolution', {
		templateUrl : 'comment.html',
		controller : 'mycomment'
	})
	
	.when('/recommendToFriend', {
		templateUrl : 'recommendation.html',
		controller : 'recommendToFriendController'
	})
	
	.when('/quizStats', {
		templateUrl : 'quizstats.html',
		controller : 'quizStatsController'
	})
	
	.when('/quizsearch', {
		templateUrl : 'quizsearch.html',
		controller : 'quizSearchController'
	})
	
	.when('/startquiz', {
		templateUrl : 'startquiz.html',
		controller : 'startQuizController'
	})
	
	.otherwise({
		redirectTo : '/'
	});
});

quizapp.controller('homeController', function($scope, $http, $location, $q,
		dataSharing, $timeout, $rootScope) {
	console.log('homeController start');
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;

	$scope.loginform_login = function(item, event) {
		console.log("--> Submitting form "
				+ $scope.loginform_email + " "
				+ $scope.loginform_password);
		console.log("--> Submitting form ");
		var data = {
			email : $scope.loginform_email,
			password : $scope.loginform_password
		};

		var response = $http.post("../../quizme/login", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					$location.url('/home');
				});
		response.error(function(data, status, headers, config) {
			console.log(data.errorMessage);
			console.log(status);
			if(status === 400){
				$scope.error = data.errorMessage;
			}
			return $q.reject(response);
		});
	};

	$scope.clickRegister = function() {
		$location.url('/register');
	}

	console.log('homeController end');
});

quizapp.controller('registerController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('registerController start');
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;

	$scope.signupform_signup = function(item, event) {
		console.log("--> Submitting form "
				+ $scope.signupform_name + " "
				+ $scope.signupform_email );
		console.log("--> Submitting form "
				+ $scope.signupform_phone + " "
				+ $scope.signupform_address);
		console.log("--> Submitting form "
				+ $scope.signupform_city + " "
				+ $scope.signupform_state+" "
				+ $scope.signupform_country);
		console.log("--> Submitting form ");
		
		var data = {
				email : $scope.signupform_email,
				password : $scope.signupform_password,
				phonenumber: $scope.signupform_phone,
				country: $scope.signupform_country
			};
		
		var response = $http.post("../../quizme/createuser", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					$scope.success = "User Added Successfully";
				});
		response.error(function(data, status, headers, config) {
			console.log(data.errorMessage);
			console.log(status);
			if(status === 400){
				$scope.error = data.errorMessage;
			}
			return $q.reject(response);
		});	

	};
	console.log('registerController end');
});


quizapp.controller('quizhomeController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('quizhomeController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//getUserCreatedQuiz
	var dataForGetUserCreatedQuiz = {
			"scoreForUser":{
				"email":"swap@localhost.com",
			},
			"scoreForQuiz":{
				"quizId":"100",
				"quizName":"Tech Quiz",
				"quizCategory":"Computer"
			},
			"score":100000,
			"rankForQuiz":{
				"rank":"10000",
				"category":"quizwise",
				"score":"99"
			}
	};
	
	var dataFormServer = new Array();
	for(var i=0; i<20; i++){
		dataFormServer[i] = dataForGetUserCreatedQuiz;
	}

	$scope.queue = {
			transactions: []
	};
	for(var i=0; i<20; i++){
		$scope.queue.transactions.push(dataFormServer[i]);
	}

	$scope.createQuiz = function(item, event) {
		console.log("create quiz");
		$location.url("/createquiz");
	};

	$scope.openQuizSolution = function(item, event) {
		console.log("take quiz");
		$location.url("/quizsearch");
		//$location.url("/quizsolution");
	};
	
	$scope.showQuizStats = function(quizid) {
		console.log("showQuizStats");
		//get data for quiz and pass to next page
		var quizStatsData = {
				"quiz": {
					"quizName":"science quiz",
					"quizDescription":"this is my first quiz",
					"quizDiffficulty":"Hard",
					"quizCategory":"science"
				},
				"lowestScore":0,
				"highestScore":80,
				"averageScore":50.76,
				"totalQuizTakers":200
		};
		var shareData = new Array();
		shareData["quizStatsData"] = quizStatsData;
		dataSharing.set(shareData);
		$location.url("/quizStats");
	};
	
	console.log('quizhomeController end');
});

quizapp.controller('createquizController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('createquizController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//structure to generate quiz
	var questionCounter = 0;
	$scope.questionData = {
			"no":questionCounter+1,
			"questionstring":" ",
			"optionStringFromUI":[" "],
			"correctionoptionstring":" "
	}

	//add more options
	$scope.addOption = function(item, event) {
		console.log("add Option");
		console.log("add Option length "+$scope.questionData.optionStringFromUI.length);
		$scope.questionData.optionStringFromUI.splice($scope.questionData.optionStringFromUI.length, 0, " ");
		console.log("add Option length after "+$scope.questionData.optionStringFromUI.length);
	};

	//delete options
	$scope.deleteOption = function(optionid) {
		console.log("delete Option");
		console.log("delete Option length "+$scope.questionData.optionStringFromUI.length);
		$scope.questionData.optionStringFromUI.splice(optionid, 1);
		console.log("delete Option length after "+$scope.questionData.optionStringFromUI.length);
	};

	//quiz data
	$scope.quizData = {
			"quizname":" ",
			"quizdescription":" ",
			"categoryid":" ",
			"quizlevel":" ",
			"questions":[]
	}

	//add question
	$scope.addQuestion = function() {
		console.log("add question");
		var addQuestionData = {
				"no":questionCounter+1,
				"questionstring":" ",
				"optionStringFromUI":[" "],
				"correctionoptionstring":" "
		}
		
		addQuestionData.questionstring = new Object($scope.questionData.questionstring);
		addQuestionData.optionStringFromUI = $scope.questionData.optionStringFromUI;
		addQuestionData.correctionoptionstring = new Object($scope.questionData.correctionoptionstring);
		console.log("add question data "+addQuestionData.questionstring);
		console.log("add question data "+addQuestionData.correctionoptionstring);
		console.log("add question data "+addQuestionData.questionstring);
		$scope.quizData.questions.splice($scope.quizData.questions.length, 0, addQuestionData);
		questionCounter++;
		//make question area empty
		$scope.questionData.questionstring = new Object(" ");
		$scope.questionData.optionStringFromUI = new Array();
		$scope.questionData.optionStringFromUI[0] = " ";
		$scope.questionData.correctionoptionstring = new Object(" ");
	};


	//add question
	$scope.deleteQuestion = function(questionid) {
		console.log("delete question");
		console.log("delete Question length "+$scope.quizData.questions.length);
		$scope.quizData.questions.splice(questionid, 1);
		console.log("delete Question length after "+$scope.quizData.questions.length);
	};
	
	//create quiz
	$scope.createQuiz = function() {
		
		var data = $scope.quizData;
		
		var response = $http.post("../../quizme/createQuiz", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					$scope.success = "Quiz Created Successfully";
				});
		response.error(function(data, status, headers, config) {
			console.log(data.errorMessage);
			console.log(status);
			if(status === 400){
				$scope.error = data.errorMessage;
			}
			return $q.reject(response);
		});	
		
	};

	console.log('createquizController end');
});




/*
 * - Puneet Popli 04/27/2015
 * User home controller
 */
quizapp.controller('homeUserController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('homeUserController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//get quiz details of a user
	var dataFormServer = new Array();
	for(var i=0; i<20; i++){
		dataFormServer[i] = {
				//"quizname1":"quiz"+i,
				"quizname1": "Java",
				"quizcreator":"puneet",
				"quizcategory":"programming",
				"quizscore":"100",
				"quizrank":"10000"
		};
	}

	$scope.queue1 = {
			transactions: []
	};
	for(var i=0; i<20; i++){
		$scope.queue1.transactions.push(dataFormServer[i]);
	}

	/*
	 * Get category ranking
	 */
	var dataFormServer1 = new Array();
	for(var j=0; j<20; j++){
		dataFormServer1[j]={
				"quizcategory1":"programming",
				"quizrank1":"10000",
				"quizscore1":"100"
		};
	}
	$scope.queue2 = {
			transactions: []
	};
	for(var i=0; i<20; i++){
		$scope.queue2.transactions.push(dataFormServer1[i]);
	}

	console.log("--> Populating global rank text box "
			+ $scope.global_rank);

	/*
	 * redirect to global dashboard
	 *
	$scope.globalDashboard = function(item, event) {
		console.log("global dashboard");
		$location.url("/globaldashboard");
	};
	 */
	console.log('homeUserController end');
});


/*
 * -Puneet Popli 04/27/2015
 * Profile Controller
 * 
 */
quizapp.controller('profileController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('profileController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//fetch data to display on profile page
	var response = $http.get("../../quizme/fetchuser");
	response.success(function(dataFromServer, status,
					headers, config) {
			$scope.profileform_email = dataFromServer.email;
			$scope.profileform_phone = dataFromServer.phonenumber;
			$scope.profileform_country = dataFromServer.country;
	});
	response.error(function(data, status, headers, config) {
		if (status == 400) {
			$scope.error = data.errorMessage;
			$location.url('/');
			return $q.reject(response);
		}else{
			$scope.error = status+": "+data.errorMessage;
			return $q.reject(response);
		}
	});
	

	$scope.profileform_edit = function(item, event) {
		
		//update user data
		var data = {
			email : $scope.profileform_email,
			password : $scope.profileform_newpassword,
			oldpassword : $scope.profileform_oldpassword,
			phonenumber: $scope.profileform_phone,
			country: $scope.profileform_country
		};
		var response = $http.put("../../quizme/updateuser", data);
		response.success(function(dataFromServer, status,
						headers, config) {
			$scope.success = "User Updated Successfully";
		});
		response.error(function(data, status, headers, config) {
			if (status == 400) {
				$scope.error = data.errorMessage;
				$location.url('/');
				return $q.reject(response);
			}else{
				$scope.error = status+": "+data.errorMessage;
				return $q.reject(response);
			}
		});
			
	};
	console.log('profileController end');
});


/*
 * -Puneet Popli 04/27/2015
 * Global Dashboard Controller
 * 
 */

quizapp.controller('globalDashboardController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('globalDashboardController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//get quiz details of a user
	var dataFormServer = new Array();
	for(var i=0; i<20; i++){
		dataFormServer[i] = {
				//"quizname":"quiz"+i,
				"gname":"puneet",
				"gscore":"100",
				"grank":"1",
				"gcountry":"India"
		};
	}

	$scope.queue1 = {
			transactions: []
	};
	for(var i=0; i<20; i++){
		$scope.queue1.transactions.push(dataFormServer[i]);
	}

	/*
	 * Select category and populate table.
	 */

	$scope.searchCategory = function(item, event) {
		console.log("search category");	

		//10 users
		var dataFormServer1 = new Array();
		for(var i=0; i<10; i++){
			dataFormServer[i] = {
					//"quizname":"quiz"+i,
					"gn2":"puneet", // name
					"gs2":"100", //category score
					"cr2":"1", //category rank
					"gr2":"10",//global rank
					"gc2":"India"	//country
			};
		}

		$scope.queue2 = {
				transactions: []
		};


		for(var i=0; i<10; i++){
			$scope.queue2.transactions.push(dataFormServer1[i]);
		}
	};

	console.log('globalDashboardController end');
});

quizapp.controller('mycomment',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('mycomment start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = {
			"quizName":"science quiz",
			"quizDescription":"this is my first quiz",
			"quizDiffficulty":"Hard",
			"quizCategory":"science",
			"quizQuestionDTOs":[
				                    {"question":"first question",
				                    "options":["option 1","option 2", "option 3","option 4"],
				                    "correctOptionId":"1",
				                    "userAnswerId":"3"},
				                    
				                    {"question":"first question",
				                    "options":["option 1","option 2", "option 3","option 4"],
				                    "correctOptionId":"2",
				                    "userAnswerId":"1"},
				                    
				                    {"question":"first question",
				                    "options":["option 1","option 2", "option 3","option 4"],
				                    "correctOptionId":"2",
				                    "userAnswerId":"1"},
				                    
				                    {"question":"first question",
				                    "options":["option 1","option 2", "option 3","option 4"],
				                    "correctOptionId":"3",
				                    "userAnswerId":"3"},
				                    
				                    {"question":"first question",
				                    "options":["option 1","option 2", "option 3","option 4"],
				                    "correctOptionId":"2",
				                    "userAnswerId":"1"},
			                    ],
			"quizCreator":"amol"
	};
	
	$scope.parseInt = parseInt;
	
	$scope.recommendToFriend = function(){
		console.log("recommendToFriend");	
		$location.url('/recommendToFriend');
		var dataTransfer = new Array();
		dataTransfer["quizData"] = $scope.quizData;
		dataSharing.set(dataTransfer);
	}
	
});


quizapp.controller('recommendToFriendController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('recommendToFriendController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = dataSharing.get().quizData;
	
	$scope.sendRecommendation = function(){
		console.log("sendRecommendation");	
		$scope.recommendationform_success = "Recommendation sent to your friend !!!!";
	}
	
});

quizapp.controller('quizStatsController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('quizStatsController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizStatsData = dataSharing.get().quizStatsData;
	
});

quizapp.controller('quizSearchController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('quizSearchController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	$scope.hideSearch = true;
	
	$scope.searchQuiz = function(){
		$scope.hideSearch = false;
		var data ={
				"serachString":$scope.searchCategory,
				"searchCategory":$scope.searchText
			};
			
			//get quiz based on search 
			var dataForQuizBasedOnSearch = {
					"scoreForUser":{
						"email":"swapp@localhost.com",
					},
					"scoreForQuiz":{
						"quizId":"100",
						"quizName":"Tech Quiz",
						"quizCategory":"Software",
						"quizCreator":{
							"email":"viresh@localhost.net"
						}
					},
					"score":100000,
					"rankForQuiz":{
						"rank":"10000",
						"category":"quizwise",
						"score":"99"
					}
			};
			
			var dataFormServer = new Array();
			for(var i=0; i<20; i++){
				dataFormServer[i] = dataForQuizBasedOnSearch;
			}

			$scope.queue = {
					transactions: []
			};
			for(var i=0; i<20; i++){
				$scope.queue.transactions.push(dataFormServer[i]);
			}
	}

	$scope.startQuiz = function(quizid) {
		console.log("start quiz");
		//get quiz by quizid
		$scope.quizData = {
				"quizName":"science quiz",
				"quizDescription":"this is my first quiz",
				"quizDiffficulty":"Hard",
				"quizCategory":"science",
				"quizQuestionDTOs":[
					                    {"question":"first question",
					                    "options":["option 1","option 2", "option 3","option 4"],
					                    "correctOptionId":"1",
					                    "userAnswerId":"3"},
					                    
					                    {"question":"first question",
					                    "options":["option 1","option 2", "option 3","option 4"],
					                    "correctOptionId":"2",
					                    "userAnswerId":"1"},
					                    
					                    {"question":"first question",
					                    "options":["option 1","option 2", "option 3","option 4"],
					                    "correctOptionId":"2",
					                    "userAnswerId":"1"},
					                    
					                    {"question":"first question",
					                    "options":["option 1","option 2", "option 3","option 4"],
					                    "correctOptionId":"3",
					                    "userAnswerId":"3"},
					                    
					                    {"question":"first question",
					                    "options":["option 1","option 2", "option 3","option 4"],
					                    "correctOptionId":"2",
					                    "userAnswerId":"1"},
				                    ],
				"quizCreator":"amol"
		};
		
		var sharedData = new Array();
		sharedData["quizData"] = $scope.quizData;
		dataSharing.set(sharedData);
		
		$location.url("/startquiz");
	};
	
});

quizapp.controller('startQuizController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope, $modal) {
	console.log('startQuizController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = dataSharing.get().quizData;
	
	$scope.submitQuizConfirmModal = function(){
		
		var shareData = new Array();
		shareData["quizData"] = $scope.quizData;
		dataSharing.set(shareData);
		
		var modalInstance = $modal.open({
			 animation: $scope.animationsEnabled,
		      templateUrl: 'submitQuizConfirm.html',
		      controller: 'submitQuizConfirmController',
		      size: "sm",
		      resolve: {
		        
		      }
		    });

		  };
		  
});

quizapp.controller('submitQuizConfirmController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope, $modalInstance) {
	console.log('submitQuizConfirmController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	 $scope.submitQuiz = function () {
		$scope.quizData = dataSharing.get().quizData;
	    console.info("quiz submit data "+$scope.quizData.quizName);
	    $modalInstance.close();
	    $location.url("/home");
	  };

	  $scope.cancelSubmit = function () {
	    $modalInstance.dismiss('cancel');
	  };
});



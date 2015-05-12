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
	
	.when('/logout', {
		templateUrl : 'home.html',
		controller : 'logoutController'
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
				name : $scope.signupform_name,
				email : $scope.signupform_email,
				newpassword : $scope.signupform_password,
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
	
	
	$scope.success = "";
	$scope.hideUserCreatedQuizTable = true;

	//get user created quiz
	var response = $http.get("../../quizme/getUserCreatedQuiz");
	response
			.success(function(dataFromServer, status,
					headers, config) {
				if(dataFromServer != ""){
					$scope.queue = {
							transactions: []
					};
					for(var i=0; i<dataFromServer.length; i++){
						
						$scope.queue.transactions.push(dataFromServer[i]);
					}
					$scope.hideUserCreatedQuizTable = false;
				}else{
					$scope.success = "User hasn't created any quiz yet !!";
					$scope.hideUserCreatedQuizTable = true;
				}
				
			});
	response.error(function(data, status, headers, config) {
		console.log(data.errorMessage);
		console.log(status);
		if(status === 400){
			$scope.error = data.errorMessage;
		}
		$location.url("/");
		return $q.reject(response);
	});	

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
		var data = {
				quizid : quizid
			};
		
		var response = $http.post("../../quizme/getQuizStats", data,
				{});
		response
				.success(function(dataFromServer, status,
						headers, config) {
					var shareData = new Array();
					shareData["quizStatsData"] = dataFromServer;
					dataSharing.set(shareData);
					$location.url("/quizStats");
				});
		response.error(function(data, status, headers, config) {
			console.log(data.errorMessage);
			console.log(status);
			if(status === 400){
				$scope.error = data.errorMessage;
			}
			$location.url("/");
			return $q.reject(response);
		});	
		
		/*var quizStatsData = {
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
		};*/
	};
	
	console.log('quizhomeController end');
});

quizapp.controller('createquizController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('createquizController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	$scope.disableCreateQuiz = true;
	
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
			"category":" ",
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
		
		if(questionCounter < 5){
			$scope.disableCreateQuiz = true;
		}else{
			$scope.disableCreateQuiz = false;
		}
	};


	//add question
	$scope.deleteQuestion = function(questionid) {
		
		console.log("delete question");
		console.log("delete Question length "+$scope.quizData.questions.length);
		$scope.quizData.questions.splice(questionid, 1);
		console.log("delete Question length after "+$scope.quizData.questions.length);
		questionCounter = questionCounter - 1;
		
		if(questionCounter < 5){
			$scope.disableCreateQuiz = true;
		}else{
			$scope.disableCreateQuiz = false;
		}
	};
	
	//create quiz
	$scope.createQuiz = function() {
		
		if(questionCounter < 5){
			$scope.disableCreateQuiz = true;
		}else{
			$scope.disableCreateQuiz = false;
		}
		
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
			$location.url("/");
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

	$scope.hideAttemptedQuizTable = false;
	$scope.hideCategoryAttemptedQuizTable = false;
	$scope.message1 = "";
	$scope.message2 ="";
	
	//get global rank
	var response = $http.get("../../quizme/getGlobalRank");
	response
		.success(function(dataFromServer, status,
				headers, config) {
			$scope.globalRank = dataFromServer.globalRank;
		});
	response.error(function(data, status, headers, config) {
		console.log(data.errorMessage);
		console.log(status);
		if(status == 400){
			$scope.error = data.errorMessage;
		}
		$location.url("/");
		return $q.reject(response);
	});
	
	//get quiz details of a user
	response = $http.get("../../quizme/getAttemptedQuizes");
	response
			.success(function(dataFromServer, status,
					headers, config) {
				
				console.log("getAttemptedQuizes dataFormServer "+dataFromServer);
				if(dataFromServer.length > 0){
					$scope.queue1 = {
							transactions: []
					};
					for(var i=0; i<dataFromServer.length; i++){
						$scope.queue1.transactions.push(dataFromServer[i]);
					}
				}else{
					$scope.message1 = "User hasn't attempted any quiz yet !!";
					$scope.queue1 = {
							transactions: []
					};
					$scope.hideAttemptedQuizTable = true;
				}
				
			});
	response.error(function(data, status, headers, config) {
		console.log(data.errorMessage);
		console.log(status);
		if(status === 400){
			$scope.message1 = data.errorMessage;
			$scope.queue1 = {
					transactions: []
			};
			$scope.hideAttemptedQuizTable = true;
		}
		$location.url("/");
		return $q.reject(response);
	});	
	
	
	//get category wise score and rank
	//get quiz details of a user
	response = $http.get("../../quizme/getCategoryTopScoreAndRank");
	response.success(function(dataFromServer, status,
					headers, config) {
				
				console.log("getCategoryTopScoreAndRank dataFormServer "+dataFromServer);
				if(dataFromServer.length > 0){
					$scope.queue2 = {
							transactions: []
					};
					for(var i=0; i<dataFromServer.length; i++){
						$scope.queue2.transactions.push(dataFromServer[i]);
					}
				}else{
					$scope.message2 = "User hasn't attempted any quiz yet !!";
					$scope.queue2 = {
							transactions: []
					};
					$scope.hideCategoryAttemptedQuizTable = true;
				}
				
			});
	response.error(function(data, status, headers, config) {
		console.log(data.errorMessage);
		console.log(status);
		if(status === 400){
			$scope.message2 = data.errorMessage;
			$scope.queue2 = {
					transactions: []
			};
			$scope.hideCategoryAttemptedQuizTable = true;
		}
		$location.url("/");
		return $q.reject(response);
	});	

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
			$scope.profileform_name = dataFromServer.name;
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
		$location.url("/");
	});
	

	$scope.profileform_edit = function(item, event) {
		
		//update user data
		var data = {
			email : $scope.profileform_email,
			newpassword : $scope.profileform_newpassword,
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
			$location.url("/");
		});
			
	};
	console.log('profileController end');
});


quizapp.controller('globalDashboardController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('globalDashboardController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	$scope.showCategoryTop = true;
	
	//get toppers
	var response = $http.get("../../quizme/getTopScorer");
	response.success(function(dataFromServer, status,
					headers, config) {
		$scope.queue1 = {
				transactions: []
		};
		for(var i=0; i<dataFromServer.length; i++){
			$scope.queue1.transactions.push(dataFromServer[i]);
		}
		$scope.itemsByPage1 = 10;
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
		$location.url("/");
	});
	
	/*
	 * Select category and populate table.
	 */

	$scope.searchCategory = function() {
		console.log("search category");	
		
		//gettopscorecategorywise
		var data ={
				"category":$scope.globaldashboard_category
			};
		response = $http.post("../../quizme/getTopScoreCategorywise", data);
		response.success(function(dataFromServer, status,
						headers, config) {
			$scope.queue2 = {
					transactions: []
			};
			for(var i=0; i<dataFromServer.length; i++){
				$scope.queue2.transactions.push(dataFromServer[i]);
			}
			$scope.itemsByPage2 = 10;
			$scope.showCategoryTop = false;
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
			$location.url("/");
		});
	};

	console.log('globalDashboardController end');
});

quizapp.controller('mycomment',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope, $route) {
	console.log('mycomment start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = dataSharing.get().quizData;
	$scope.quizSubmitData = dataSharing.get().quizSubmitData;
	$scope.quizResult = dataSharing.get().quizResult;
	$scope.questionNo = 0;
	
	$scope.parseInt = parseInt;
	
	$scope.recommendToFriend = function(){
		console.log("recommendToFriend");	
		var dataTransfer = new Array();
		dataTransfer["quizData"] = $scope.quizData;
		dataSharing.set(dataTransfer);
		$location.url('/recommendToFriend');
	}
	
	//get all comments for quiz
	//update user data
	var data = {
		"quizid":$scope.quizData.quizid,
	};
	var response = $http.post("../../quizme/getAllComments", data);
	response.success(function(dataFromServer, status,
					headers, config) {
			$scope.queue = {
					transactions: []
			};
			for(var i=0; i<dataFromServer.length; i++){
				$scope.queue.transactions.push(dataFromServer[i]);
			}
			console.log(dataFromServer);
			
		
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
		$location.url("/");
	});
	
	$scope.commentform_postComment = function(){
		console.log("commentform_postComment");	
		//update user data
		var data = {
			"quizid":$scope.quizData.quizid,
			"comment":$scope.commentform_comment
		};
		var response = $http.post("../../quizme/postComment", data);
		response.success(function(dataFromServer, status,
						headers, config) {
			//$location.url("/quizsolution");
			$route.reload();
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
			$location.url("/");
		});
	}
	
});


quizapp.controller('recommendToFriendController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('recommendToFriendController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = dataSharing.get().quizData;
	console.log("data re "+$scope.quizData);
	
	$scope.sendRecommendation = function(){
		console.log("sendRecommendation");	
		var data = {
				"quizid":$scope.quizData.quizid,
				"username":$scope.recommendationform_recemail
			};
			var response = $http.post("../../quizme/shareQuiz", data);
			response.success(function(dataFromServer, status,
							headers, config) {
				console.log(dataFromServer.successFlag);
				if(dataFromServer.successFlag == true){
					$scope.recommendationform_success = "Recommendation sent to your friend !!!!";
				}else{
					$scope.recommendationform_error = dataFromServer.applicationMessage;
				}
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
				$location.url("/");
			});
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
	
	$scope.searchCategory = "0";
	$scope.hideSearchTextBox = true;
	$scope.hideSearchCategoryDropDown = false;
	$scope.hideSearchQuizLevelDropDown = true;
	
	$scope.changedQuizSearch = function(){
		if($scope.searchCategory == "0"){
			$scope.hideSearchTextBox = true;
			$scope.hideSearchCategoryDropDown = false;
			$scope.hideSearchQuizLevelDropDown = true;
		}else if($scope.searchCategory == "1"){
			$scope.hideSearchTextBox = true;
			$scope.hideSearchCategoryDropDown = true;
			$scope.hideSearchQuizLevelDropDown = false;
		}else{
			$scope.hideSearchTextBox = false;
			$scope.hideSearchCategoryDropDown = true;
			$scope.hideSearchQuizLevelDropDown = true;
		}
	}
	
	$scope.searchQuiz = function(){
		$scope.error = "";
		$scope.hideSearch = false;
		var searchString = $scope.searchText;
		if($scope.searchCategory == "0"){
			searchString = $scope.searchTextForCategory;
		}else if($scope.searchCategory == "1"){
			searchString = $scope.searchTextForQuizLevel;
		}else{
			searchString = $scope.searchText;
		}
		var data ={
				"searchId":$scope.searchCategory,
				"serachString":searchString
			};
			
			var response = $http.post("../../quizme/searchQuiz", data);
			response.success(function(dataFromServer, status,
							headers, config) {
				console.log("dataFromServer "+dataFromServer);
				$scope.queue = {
						transactions: []
				};
				for(var i=0; i<dataFromServer.length; i++){
					$scope.queue.transactions.push(dataFromServer[i]);
				}
			});
			response.error(function(data, status, headers, config) {
				if (status == 400) {
					$scope.queue = {
							transactions: []
					};
					$scope.error = data.errorMessage;
					$scope.hideSearch = true;
					return $q.reject(response);
				}else{
					$scope.error = status+": "+data.errorMessage;
					return $q.reject(response);
				}
				$location.url("/");
			});
			
	}

	$scope.startQuiz = function(quizid) {
		console.log("start quiz "+quizid);
		//get quiz by quizid
		var data ={
				"searchId":quizid,
				"serachString":""
			};
			
			var response = $http.post("../../quizme/getQuiz", data);
			response.success(function(dataFromServer, status,
							headers, config) {
				var sharedData = new Array();
				sharedData["quizData"] = dataFromServer;
				dataSharing.set(sharedData);
				$location.url("/startquiz");
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
				$location.url("/");
			});
		
		/*$scope.quizData = {
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
		};*/
		
	};
	
});

quizapp.controller('startQuizController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope, $modal) {
	console.log('startQuizController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	$scope.quizData = dataSharing.get().quizData;
	$scope.questionumber = 0;
	//question and answer
	$scope.quizAnswers = new Array();
	for(var i=0; i<$scope.quizData.questions.length; i++){
		var questionAndAns = new Object();
		questionAndAns["questionid"] = $scope.quizData.questions[i].questionid;
		questionAndAns["userselectedoptionoid"] = 0;
		$scope.quizAnswers[i] = questionAndAns;
	}
	console.log("quizAnswers "+$scope.quizAnswers.length);
	
	$scope.quizSubmitData = {
		"quizid": $scope.quizData.quizid,
		"setOfQuestionAndAnswers":$scope.quizAnswers
	};
	
	$scope.submitQuizConfirmModal = function(){
		
		var shareData = new Array();
		shareData["quizData"] = $scope.quizData;
		shareData["quizSubmitData"] = $scope.quizSubmitData;
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
		$scope.quizSubmitData = dataSharing.get().quizSubmitData;
		//evaluate quiz
		var response = $http.post("../../quizme/submitQuiz", $scope.quizSubmitData);
		var shareData = new Array();
		response.success(function(dataFromServer, status,
						headers, config) {
			console.log("evaluate result "+dataFromServer);
			shareData["quizData"] = $scope.quizData;
			shareData["quizSubmitData"] = $scope.quizSubmitData;
			shareData["quizResult"] = dataFromServer;
			dataSharing.set(shareData);
			console.info("quiz submit data "+shareData);
		    $modalInstance.close();
		    $location.url("/quizsolution");
		});
		response.error(function(data, status, headers, config) {
			if (status == 400) {
				$scope.error = data.errorMessage;
				$modalInstance.close();
				$location.url('/');
				return $q.reject(response);
			}else{
				$scope.error = status+": "+data.errorMessage;
				return $q.reject(response);
			}
			$location.url("/");
		});
		
	  };

	  $scope.cancelSubmit = function () {
	    $modalInstance.dismiss('cancel');
	  };
});


quizapp.controller('logoutController', function($scope, $http, $location, $q,
		dataSharing, $timeout, $rootScope) {
	console.log('logoutController start');
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;

	var response = $http.get("../../quizme/logout");
    
	response
			.success(function(dataFromServer, status,
					headers, config) {
				$location.url('/');
			});
	response.error(function(data, status, headers, config) {
		if (response.status === 401
				|| response.status === 400) {
			$location.url('/');
			return $q.reject(response);
		}
	});

	console.log('homeController end');
});


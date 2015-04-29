var quizapp = angular.module('quizapp', [ 'ngRoute', 'ngResource', 'smart-table' ]);

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
		console.log("--> Submitting form " + $scope.loginform_email + " "
				+ $scope.loginform_password);
		console.log("--> Submitting form ");

		$location.url('/home');
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

	};
	console.log('registerController end');
});


quizapp.controller('quizhomeController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('quizhomeController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;

	//get user created quiz data
	var dataFormServer = new Array();
	for(var i=0; i<20; i++){
		dataFormServer[i] = {
				"quizname":"quiz"+i,
				"quizcreator":"swap",
				"quizcategory":"science",
				"quizmaxscore":"100",
				"quiztopper":"swap"
		};
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
			"question":" ",
			"options":[" "],
			"correct_option_id":" "
	}

	//add more options
	$scope.addOption = function(item, event) {
		console.log("add Option");
		console.log("add Option length "+$scope.questionData.options.length);
		$scope.questionData.options.splice($scope.questionData.options.length, 0, " ");
		console.log("add Option length after "+$scope.questionData.options.length);
	};

	//delete options
	$scope.deleteOption = function(optionid) {
		console.log("delete Option");
		console.log("delete Option length "+$scope.questionData.options.length);
		$scope.questionData.options.splice(optionid, 1);
		console.log("delete Option length after "+$scope.questionData.options.length);
	};

	//quiz data
	$scope.quizData = {
			"quizname":" ",
			"quizdescription":" ",
			"quizcategory":" ",
			"quizdifficulty":" ",
			"quizquestions":[]
	}

	//add question
	$scope.addQuestion = function() {
		console.log("add question");
		var addQuestionData = {
				"no":questionCounter+1,
				"question":" ",
				"options":[" "],
				"correct_option_id":" "
		}
		addQuestionData.question = new Object($scope.questionData.question);
		addQuestionData.options = $scope.questionData.options;
		addQuestionData.correct_option_id = new Object($scope.questionData.correct_option_id);
		console.log("add question data "+addQuestionData.question);
		console.log("add question data "+addQuestionData.correct_option_id);
		console.log("add question data "+addQuestionData.question);
		$scope.quizData.quizquestions.splice($scope.quizData.quizquestions.length, 0, addQuestionData);
		questionCounter++;
		//make question area empty
		$scope.questionData.question = new Object(" ");
		$scope.questionData.options = new Array();
		$scope.questionData.options[0] = " ";
		$scope.questionData.correct_option_id = new Object(" ");
	};


	//add question
	$scope.deleteQuestion = function(questionid) {
		console.log("delete question");
		console.log("delete Question length "+$scope.quizData.quizquestions.length);
		$scope.quizData.quizquestions.splice(questionid, 1);
		console.log("delete Question length after "+$scope.quizData.quizquestions.length);
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
	$rootScope.hideUserNavTabs = true;
	$rootScope.hideStaticTabs = false;


	$scope.profileform_edit = function(item, event) {
		console.log("--> Editing form "
				+ $scope.profileform_name + " "
				+ $scope.profileform_email );
		/*
		 * enter old password
		 */
		console.log("--> Editing form "
				+ $scope.profileform_oldpassword + " "
				+ $scope.profileform_oldpassword );
		console.log("--> Editing form "
				+ $scope.profileform_phone + " "
				+ $scope.profileform_address);
		console.log("--> Editing form "
				+ $scope.profileform_city + " "
				+ $scope.profileform_state+" "
				+ $scope.profileform_country);
		console.log("--> Editing form ");

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




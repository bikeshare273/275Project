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

// configure our routes
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

	.when('/home', {
		templateUrl : 'HomeUser.html',
		controller : 'homeUserController'
	})
	
	.when('/quizhome', {
		templateUrl : 'quizhome.html',
		controller : 'quizhomeController'
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

quizapp.controller('homeUserController',
		function($scope, $http, $location, $q, dataSharing, $timeout, $rootScope) {
	console.log('homeUserController start');
	$rootScope.hideUserNavTabs = false;
	$rootScope.hideStaticTabs = true;
	
	
	console.log('homeUserController end');
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
		addQuestionData.options = new Array($scope.questionData.options);
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
	
	console.log('createquizController end');
});


(function(){
	'use strict';
	angular.module('Phonebook')
	.component('groupsList', {
		templateUrl: 'view/groupslist.html',
		bindings: {
			groups: '<',
			onRemove:'&'
			
		}
	});
	
})();
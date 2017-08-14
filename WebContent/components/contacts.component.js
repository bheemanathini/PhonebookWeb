(function(){
	'use strict';
	angular.module('Phonebook')
	.component('contactsList', {
		templateUrl: 'view/contactslist.html',
		bindings: {
			contacts: '<',
			onRemove: '&',
			sort: '=',
			reverse: '='

			
		}
	});
	
})();
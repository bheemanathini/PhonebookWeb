(function(){
	'use strict';
	angular.module('Phonebook')
	.controller('AddContactController', AddContactController);

	AddContactController.$inject = ['$http', '$state', 'ContactsService']
	function AddContactController($http, $state, ContactsService){
		console.log("add contact controller called");

		var addContactCtrl = this;

		addContactCtrl.contact = {};


		addContactCtrl.submit = function(){
			ContactsService.addContact(addContactCtrl.contact);

		}


	}

})();